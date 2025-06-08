package io.github.aj8gh.trading

import org.intellij.lang.annotations.Language

@Language("json")
const val TRADES_PAYLOAD = """
  [
    {
      "commodity": "Gold",
      "traderId": "T123",
      "price": 2023.5,
      "quantity": 10,
      "timestamp": "2025-04-10T14:30:00Z"
    },
    {
      "commodity": "Oil",
      "traderId": "T456",
      "price": 85.2,
      "quantity": 100,
      "timestamp": "2025-04-10T15:10:00Z"
    }
  ]
  """

@Language("json")
const val INSIGHTS_RESPONSE = """
  {
    "totalVolumeByCommodity": {
      "Gold": 10,
      "Oil": 100
    },
    "averagePriceByCommodity": {
      "Gold": 2023.5,
      "Oil": 85.2
    },
    "topTradersByVolume": [
      {
        "traderId": "T456",
        "volume": 100
      },
      {
        "traderId": "T123",
        "volume": 10
      }
    ]
  } 
  """

@Language("json")
const val TRADES_PAYLOAD_1 = """
  [
    {
      "commodity": "Gold",
      "traderId": "T123",
      "price": 100.5,
      "quantity": 3,
      "timestamp": "2025-04-10T14:30:00Z"
    },
    {
      "commodity": "Oil",
      "traderId": "T123",
      "price": 200.2,
      "quantity":20,
      "timestamp": "2025-04-10T15:10:00Z"
    },
    {
      "commodity": "Gold",
      "traderId": "T456",
      "price": 500.7,
      "quantity": 10,
      "timestamp": "2025-04-10T16:05:00Z"
    },
    {
      "commodity": "Silver",
      "traderId": "T789",
      "price": 100.7,
      "quantity": 29,
      "timestamp": "2025-04-10T16:08:00Z"
    },
    {
      "commodity": "Oil",
      "traderId": "T789",
      "price": 250.8,
      "quantity": 72,
      "timestamp": "2025-04-10T16:12:00Z"
    }
  ]
  """

@Language("json")
const val INSIGHTS_RESPONSE_1 = """
  {
    "totalVolumeByCommodity": {
      "Gold": 13,
      "Oil": 92,
      "Silver": 29
    },
    "averagePriceByCommodity": {
      "Gold": 300.6,
      "Oil": 225.5,
      "Silver": 100.7
    },
    "topTradersByVolume": [
      {
        "traderId": "T789",
        "volume": 101
      },
      {
        "traderId": "T123",
        "volume": 23
      },
      {
        "traderId": "T456",
        "volume": 10
      }
    ]
  } 
  """

@Language("json")
val BAD_REQUEST_PAYLOADS = listOf(
  """
  [
    {
      "traderId": "T123",
      "price": 2023.5,
      "quantity": 10,
      "timestamp": "2025-04-10T14:30:00Z"
    }
  ]
  """,
  """
  [
    {
      "commodity": "Gold",
      "price": 2023.5,
      "quantity": 10,
      "timestamp": "2025-04-10T14:30:00Z"
    }
  ]
  """,
  """
  [
    {
      "traderId": "T123",
      "commodity": "Gold",
      "quantity": 10,
      "timestamp": "2025-04-10T14:30:00Z"
    }
  ]
  """,
  """
  [
    {
      "commodity": "Gold",
      "traderId": "T123",
      "price": 2023.5,
      "quantity": 10
    }
  ]
  """,
  """
  [
    {
      "traderId": "T123",
      "price": 2023.5,
      "quantity": 10,
      "timestamp": "2025-04-10T14:30:00Z"
    }
  ]
  """,

  // Invalid Fields
  """
  [
    {
      "traderId": "T123",
      "commodity": "Gold",
      "price": "invalid,
      "quantity": 10,
      "timestamp": "2025-04-10T14:30:00Z"
    }
  ]
  """,
  """
  [
    {
      "commodity": "Gold",
      "traderId": "T123",
      "price": 2023.5,
      "quantity": "invalid,
      "timestamp": "2025-04-10T14:30:00Z"
    }
  ]
  """,
  """
  [
    {
      "commodity": "Gold",
      "traderId": "T123",
      "price": 2023.5,
      "quantity": 10,
      "timestamp": "invalid"
    }
  ]
  """,

  // Invalid JSON
  """
  [
    
      "commodity": "Gold",
      "traderId": "T123",
      "price": 2023.5,
      "quantity": 10,
      "timestamp": "invalid"
    
  ]
  """,

  // Empty body
  """
  """,
)
