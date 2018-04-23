# TopJava REST API Documentation

> **Base URL:** http://youhost.com/topjava/rest

## Meals

#### Get list of Meals
`GET  http://{Base URL}/meals`

**Example:** 
    
    curl -X GET http://localhost:8080/topjava/rest/meals 
        
**Returns:**

- Content-Type: *application/json;charset=UTF-8*


	[
	    {
	        "id": 100007,
	        "dateTime": "2015-05-31T20:00:00",
	        "description": "Dinner",
	        "calories": 510,
	        "exceed": true
	    },
	    {
	        "id": 100006,
	        "dateTime": "2015-05-31T13:00:00",
	        "description": "Lunch",
	        "calories": 1000,
	        "exceed": true
	    }
    ]
    
**Status codes:**

- **200** - OK


#### Get Meal by ID

`GET  http://{Base URL}/meals/{id}`

**Path parameters:** 

- **id** - ID of Meal


Example:

    curl -X GET http://localhost:8080/topjava/rest/meals/100005 
        


#### Get list of Meals filtered by Date and Time

`GET  http://{Base URL}/meals/filter`

**Optional query parameters:** 

- **startDate** - From date
- **endDate** - To Date
- **startTime** - From Time
- **endTime** - To Time


Example:

    curl -X GET \
      'http://localhost:8080/topjava/rest/meals/filter?startDate=2015-05-31&endDate=2015-05-31&startTime=12:00&endTime='
        


#### Delete Meal by ID

`DELETE  http://{Base URL}/meals/{id}`

**Path parameters:** 

- **id** - ID of Meal


Example: 

    curl -X DELETE http://localhost:8080/topjava/rest/meals/100005 

Returns:

 **Status codes:**
 
 - **204** - No Content
 

#### Create new meal

`POST  http://{Base URL}/meals`


**Headers:**

- Content-Type: application/json

**Request body:**

    {
    	"dateTime":"2018-04-21T22:00:00.000",
    	"description":"New_meal",
    	"calories":999
    }

Example: 

    curl -X POST \
        http://localhost:8080/topjava/rest/meals/ \
        -H 'Content-Type: application/json' \
        -d '{
    	    "dateTime":"2018-04-21T14:00:00.000",
    	    "description":"New_meal",
                "calories":999
        }'

Returns:

 **Status codes:**
 
 - **201** - Created.
 

#### Create new meal

`PUT  http://{Base URL}/meals/{id}`

**Path parameters:** 

- **id** - ID of Meal


**Headers:**

- Content-Type: application/json

**Request body:**

    {
	    "dateTime":"2015-05-31T20:00:01",
	    "description":"Update_lanch",
        "calories":777
    }

Example: 

    curl -X PUT \
        http://localhost:8080/topjava/rest/meals/100002 \
        -H 'Content-Type: application/json' \
        -d '{
	            "dateTime":"2015-05-31T20:00:01",
	            "description":"Update_lanch",
        	    "calories":777
            }'

Returns:

 **Status codes:**
 
 - **200** - OK