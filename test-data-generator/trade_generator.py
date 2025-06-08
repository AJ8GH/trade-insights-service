import os
from dataclasses import dataclass
from datetime import datetime
from datetime import timezone
from random import randint
from random import uniform
from typing import List

import click
import requests
from dataclasses_json import dataclass_json, LetterCase
from dotenv import load_dotenv

MIN_INT_VAL = 1
MIN_FLOAT_VAL = 0.1

load_dotenv()
max_price = float(os.getenv('MAX_PRICE'))
max_quantity = int(os.getenv('MAX_QUANTITY'))
max_trades = int(os.getenv('MAX_TRADES'))
price_scale = int(os.getenv('PRICE_SCALE'))
default_trades = int(os.getenv('TRADES'))
default_traders = int(os.getenv('TRADERS'))
default_commodities = int(os.getenv('COMMODITIES'))

host = os.getenv('HOST', 'localhost')
url = f'http://{host}:8080/trades'
headers = {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
}

all_commodities = [
    'Gold',
    'Oil',
    'Silver',
    'Copper',
    'Platinum',
    'Zinc',
    'Tin',
    'Cobalt',
    'Aluminium',
    'Coffee',
    'Corn',
    'Cocoa',
    'Natural Gas',
    'Wheat',
]


@dataclass_json(letter_case=LetterCase.CAMEL)
@dataclass
class Trade:
    trader_id: str
    commodity: str
    quantity: int
    price: float
    timestamp: str


@click.command()
@click.option('--trades', '-t', default=default_trades, help='Number of trades to generate.')
@click.option('--traders', '-tr', default=default_traders, help='Number of traders to generate trades for.')
@click.option('--commodities', '-c', default=default_commodities, help='Number of commodities to be traded.')
def post_trades(trades: int, traders: int, commodities: int) -> None:
    commodities = min(max(commodities, MIN_INT_VAL), len(all_commodities))
    trades = min(max(trades, MIN_INT_VAL), max_trades)
    traders = min(max(traders, MIN_INT_VAL), trades)
    click.echo(f'Posting {trades} trades for {traders} traders and {commodities} commodities to {url}')

    trades_to_post = build_trades(trades=trades, traders=traders, commodities=commodities)
    payload = Trade.schema().dumps(trades_to_post, many=True)
    response = requests.post(url=url, data=payload, headers=headers)
    click.echo(f'Response status: {response.status_code}')


def build_trades(trades: int, traders: int, commodities: int) -> List[Trade]:
    trades_to_post = []
    for i in range(trades):
        trader_id = f'T{(i % traders) + 1}'
        commodity = all_commodities[i % commodities]
        trade = build_trade(trader_id=trader_id, commodity=commodity)
        trades_to_post.append(trade)
    return trades_to_post


def build_trade(trader_id: str, commodity: str) -> Trade:
    price = round(number=uniform(MIN_FLOAT_VAL, max_price), ndigits=price_scale)
    quantity = randint(MIN_INT_VAL, max_quantity)
    timestamp = datetime.now(timezone.utc).isoformat()
    return Trade(
        trader_id=trader_id,
        commodity=commodity,
        price=price,
        quantity=quantity,
        timestamp=timestamp,
    )


if __name__ == '__main__':
    post_trades()
