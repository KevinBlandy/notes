db.user.aggregate([
  {$match: {"funding_rounds.investments.financial_org.permalink": "greylock"} },
  {$project: {
    _id: 0,
    name: 1,
    amount: "$funding_rounds.raised_amount",
    year: "$funding_rounds.funded_year"
  }}
])

db.user.aggregate([
	{"$match": {"funding_rounds.investments.financial_org.permalink": "greylock"} },
	{"$unwind": "$funding_rounds"},
	{"$project": {
		"_id": 0,
		"name": 1,
		"amount": "$funding_rounds.raised_amount",
		"year": "$funding_rounds.funded_year"
	}}
])

{
  "_id" : "52cdef7c4bab8bd675297d8e",
  "name" : "Facebook",
  "category_code" : "social",
  "founded_year" : 2004,
  "description" : "Social network",
 // 融资
  "funding_rounds" : [{
      "id" : 4,
      "round_code" : "b",
      "raised_amount" : 27500000,
      "raised_currency_code" : "USD",
		  // 融资年分
      "funded_year" : 2006,
		  // 投资人
      "investments" : [
        {
          "company" : null,
			  // 金融组织
          "financial_org" : {
            "name" : "Greylock Partners",
            "permalink" : "greylock"
          },
          "person" : null
        },
        {
          "company" : null,
          "financial_org" : {
            "name" : "Meritech Capital Partners",
            "permalink" : "meritech-capital-partners"
          },
          "person" : null
        },
        {
          "company" : null,
          "financial_org" : {
            "name" : "Founders Fund",
            "permalink" : "founders-fund"
          },
          "person" : null
        },
        {
          "company" : null,
          "financial_org" : {
            "name" : "SV Angel",
            "permalink" : "sv-angel"
          },
          "person" : null
        }
      ]
    },
    {
      "id" : 2197,
      "round_code" : "c",
      "raised_amount" : 15000000,
      "raised_currency_code" : "USD",
      "funded_year" : 2008,
      "investments" : [
        {
          "company" : null,
          "financial_org" : {
            "name" : "European Founders Fund",
            "permalink" : "european-founders-fund"
          },
          "person" : null
        }
      ]
    }],
  "ipo" : {
    "valuation_amount" : NumberLong("104000000000"),
    "valuation_currency_code" : "USD",
    "pub_year" : 2012,
    "pub_month" : 5,
    "pub_day" : 18,
    "stock_symbol" : "NASDAQ:FB"
  }
}