# TopJava REST API Documentation

Base URL: http://youhost.com/topjava/rest

## Meals

#### Get all Meal
 
**GET**  http  /meals

Example: 
> [http://localhost:8080/topjava/rest/meals](http://localhost:8080/topjava/rest/meals)


Returns:

Content-Type: application/json

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
	    },
	    {
	        "id": 100005,
	        "dateTime": "2015-05-31T10:00:00",
	        "description": "Breakfast",
	        "calories": 500,
	        "exceed": true
	    }
    ]
    
Status codes:

200 OK	The service call has completed successfully.

500 Internal Server Error	Internal server exception. The service call did not succeed.



##### Get Meal by ID
 
GET http://localhost:8080/topjava/rest/meals/**{mealId}**

Path parameters 

id - ID of Meal


Example: [http://localhost:8080/topjava/rest/meals/100005](http://localhost:8080/topjava/rest/meals/100005) 


Returns:

Content-Type: application/json

    {
        "id": 100005,
        "dateTime": "2015-05-31T10:00:00",
        "description": "Breakfast",
        "calories": 500,
        "user": null
    }
