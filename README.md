FORMAT: 1A
HOST: localhost:8080/api (or the next available port, see console output of back end for the port that the back end is actually listening on)


# Web Engineering API Design

This API provides insights to the Million Song Dataset made available by the CORGIS dataset project 
and can be found at https://think.cs.vt.edu/corgis/csv/music/.

Web Engineering project 19-20 by Cornelis Zwart and Richard Westerhof.

# Contents

## [Artists Collection](#artistsCollection)

+ [Get All Artists](#getArtists)
+ [Get Artist By ID](#getArtist)
+ [Get Artist Statistics](#getArtistStats)
+ [Get Artists By Popularity](#getArtistPopularity)
+ [Add Artist](#addArtist)
+ [Remove Artist](#removeArtist)
+ [Update Artist](#updateArtist)


## [Songs Collection](#songsCollection)
+ [Get All Songs](#getSongs)
+ [Get Song By Id](#getSong)
+ [Get Songs By Popularity](#songsByPopularity)
+ [Add Song](#addSong)
+ [Update Song](#updateSong)


## [Supported Methods](#methods)


## Remarks
 1. Note that every request that returns any data in the body of the response has an optional header for the representation of that data. It looks as follows

	+ Accept (="application/json" OR "text/csv")
    
	This header can have the value "application/json" or "text/csv", it will default to json if an invalid value is given, or if the header is left out completely.
	The representation will be reflected in the content type header of the response and looks as follows:

	+ Content-Type (="application/json" OR "text/csv")
	
2. Note that all requests should go to localhost:8080/**api** and that each specific part of a uri is relative to the section it belongs to. This means that in order to get the statistics of an artist with id = 1 and from 2019, the full uri would look like this: 
	
	+ localhost:8080/api/artists/1/statistics?year=2019
	

## <a name="artistsCollection"></a> Artists Collection [/artists]

### <a name="getArtists"></a> Get All Artists [GET ?name={name}&genre={genre}]

+ Query Parameters
    + name (string) - name of the artist
    + genre (string) - genre of artist

+ Response 200 (application/json)

        [
            {
                "id": 1,
                "name": "Casual",
                "term": "hiphop"
                "pageLink": "/api/artists/1"
            },
            {
                "id": "2",
                "name": "TheBoxTops",
                "term": "blue-eyedsoul"
                "pageLink": "/api/artists/2"
            },
            etc.
        ]
        
+ Response 200 (text/csv)

        "id", "name", "term", "pageLink"
        "1", "Casual", "hiphop", "/api/artists/1"
        "2", "TheBoxTops", "blue-eyedsoul", "/api/artists/2"
        etc.    
        
        
### <a name="getArtist"></a> Get Artist By ID [GET /{artistId}]

+ Path Parameters
    + artistId (string) - id of the artist

+ Response 200 (application/json)

        {
            "id": "1",
            "name": "Casual",
            "term": "hiphop",
            "pageLink": "/api/artists/1"
        }
        
+ Response 200 (text/csv)

        "id", "name", "term", "linkToArtistsSongs"
        "1", "Casual", "hiphop", "api/artists/1"
	

### <a name="getArtistStats"></a> Get Artist Statistics [GET /{artistId}/statistics?year={year}]

+ Path Parameters
    + artistId (string) - ID of the artist

+ Query Parameters
    + year (number) - The year of release
    
+ Response 200 (application/json)

        {
                "mean": 0.5,
                "median": 0.5,
                "standard_deviation": 0.5
        }
        
+ Response 200 (text/csv)

        "mean", "median", "standard_deviation"
        "0.5", "0.5", "0.5"
        
        
### <a name="getArtistPopularity"></a> Get Artists By Popularity [GET /popularity?pageSize={pageSize}&pageRank={pageRank}]

+ Query Parameters
    + pageSize (number) - number of artists per page
    + pageRank (number) - the number of page queried
    
+ Response 200 (application/json)

        [
            {
                "id": 1,
                "name": "Casual",
                "term": "hiphop"
                "pageLink": "/api/artists/1"
            },
            {
                "id": "2",
                "name": "TheBoxTops",
                "term": "blue-eyedsoul"
                "pageLink": "/api/artists/2"
            },
            etc.
        ]
        
        
+ Response 200 (text/csv)

        "id", "name", "term", "pageLink"
        "1", "Casual", "hiphop", "/api/artists/1"
        "2", "TheBoxTops", "blue-eyedsoul", "/api/artists/2"
        etc.
        
        
### <a name="addArtist"></a> Add Artist [POST]

+ Request (application/json)

        {
	    	"name": "Casual",
	    	"terms": "hiphop"
		}

+ Response 201 (application/json)

    + Headers

            Location: /api/artists/1

    + Body (note that the id is the id from the original dataset, and therefore null for newly added artists)

			{
				"artistId": 1,
				"id": null,
				"name": "Casual",
				"terms": "hiphop"
			}
            
                 
+ Response 201 (text/csv)

    + Headers 
    
            Location: api/artists/1
            
    + Body
    
            "name", "id", "artistID" , "terms"
            "Casual", "null", 1, "hiphop"


### <a name="removeArtist"></a> Remove Artist [DELETE/{artistId}]

+ Parameters
    + artistId (string) - ID of the artist

+ Response 204 
			
			(no body)
			
+ Response 404 

			(no body)


### <a name="updateArtist"></a> Update Artist [PUT /{artistId}]

+ Request (application/json)
			
			{
				"name": "Casual",
				"terms": "hiphop",
				"hotness": 0.5
			}
        
+ Response 200 (application/json)

        {
            "name": "Casual",
            "terms": "hiphop",
			"hotness": 0.5
        }
        
        
+ Response 200 (text/csv)

        "name", "terms", "hotness"
        "Casual", "hiphop", 0.5
        
        
+ Response 403

		(no body)



## <a name="songsCollection"></a> Songs Collection [/songs]

### <a name="getSongs"></a> Get All Songs [GET ?title={title}&artistId={artistId}&artistName={artistName}&year={year}&term={artistTerm}]


+ Parameters 
	+ title (string) - title of the song
    + artistId (string) - id of the artist
    + artistName (string) - name of the artist
    + year (number) - The year of release
    + artistTerm (string) - The genre of the artist

+ Response 200 (application/json)

        [
            {
                "songid": 1,
				"id": null,
				"duration": 218.932,
				"hotness": 0.0,
				"title": "0",
				"year": 0,
				"artistName": "Casual",
				"artistId": 1,
				"artistLink": "/api/artists/1",
				"link": "/1"
            },
            {
                "songid": 2,
				"id": null,
				"duration": 259.448,
				"hotness": 0.0,
				"title": "0",
				"year": 0,
				"artistName": "Casual",
				"artistId": 1,
				"artistLink": "/api/artists/1",
				"link": "/2"
            },
            etc.
        ]


+ Response 200 (text/csv)

        "title", "artistsName", "duration", "year", "id", "songid", "artistid", "link", "artistLink"
        "0","Casual","218.932","0","null","1","1","/1","/api/artists/1"
		"0","Casual","259.448","0","null","2","1","/2","/api/artists/1"
        etc.


### <a name="getSong"></a> Get Information About Song By Id [GET /{songId}]

+ Path Parameters 
    + songId (string) - ID of the song

+ Response 200 (application/json)

		{
			"songid": 1,
			"id": null,
			"duration": 218.932,
			"hotness": 0.0,
			"title": "0",
			"year": 0,
			"artistName": "Casual",
			"artistId": 1,
			"artistLink": "/api/artists/1",
			"link": "/1"
		}
        
        
+ Response 200 (text/csv)

        "title", "artistsName", "duration", "year", "id", "songid", "artistid", "link", "artistLink"
        "0","Casual","218.932","0","null","1","1","/1","/api/artists/1"
               
        
### <a name="songsByPopularity"></a> Get songs by popularity [GET /popularity?year={year}&pageSize={pageSize}&pageRank={pageRank}]

+ Parameters 
    + year (number) - year of the song
    + pageSize (number) - number of artists per page
    + pageRank (number) - the number of page queried

+ Response 200 (application/json)

        [
            {
                "title": "0",
				"artistName": "Casual",
				"duration": 218.932,
				"year": 0,
				"linkToSong": "/1",
				"linkToArtist": "/api/artists/1"
            },
            {
                "title": "0",
				"artistName": "Casual",
				"duration": 259.448,
				"year": 0,
				"linkToSong": "/2",
				"linkToArtist": "/api/artists/1"
            },
            etc.
        ]
        
+ Response 200 (text/csv)

        "title", "artistsName", "duration", "year", "linkToSong", "linkToArtist"
        "0", "Casual", "218.932", "0", "/1", "/api/artists/1"
        "0", "Casual", "259.448", "0", "/2", "/api/artists/1"
        etc.
     
        
 ### <a name="addSong"></a> Add Song [POST]

+ Request (application/json)

        {
            "title": "0",
            "duration": 218.932,
			"artistsId": 1,
            "year": 0
        }

+ Response 201 (application/json)

    + Headers

            Location: /api/songs/1

    + Body

            {
				"songid": 1,
				"id": null,
				"duration": 218.932,
				"hotness": 0.0,
				"title": "0",
				"year": 0,
				"artistName": "Casual",
				"artistId": 1,
				"artistLink": "/api/artists/1",
				"link": "/1"
			}
            
            
            
            
+ Response 201 (text/csv)

    + Headers 
    
            Location: /api/songs/1
            
    + Body
    
            "title", "artistName", "duration", "year", "id", "songid", "artistid", "link", "artistLink"
			"0","Casual","218.932","0","","1","1","/1","/api/artists/1"


### <a name="updateSong"></a> Update Song [PUT /{songId}]

+ Request (application/json)
		
		{
            "title": "0",
            "duration": 218.932,
			"artistsId": 1,
            "year": 0
        }
        
+ Response 200 (application/json)

        {
            "title": "Cowboy Jim",
            "artistsName": "Jack",
            "duration": 420,
            "year": 2012,
            "linkToSong" : "/SOMZWCG12A8C13C480"
        }
             
+ Response 200 (text/csv)

        "title", "artistsName", "duration", "year", "linkToSong"
        "Cowboy Jim", "Jack", "420", "2012", "/SOMZWCG12A8C13C480"  
        
+ Response 404 
		
		(no body)


## <a name="methods"></a> Supported Methods
We decided to justify the CRUD methods supported here, instead of with each request.
This because for most GETs it's quite similar.

For searching in all artists, retrieving a single artist, artist statistics and the list of popular artists we decided to use GET. This because the goal is to retrieve data, and it is a safe request.
For artist, we also implemented a DELETE, POST and PUT.
These speak for themselves, but a quick explanation.
The DELETE removes the artists resource from the dataset if they request to do so, because of privacy for example.
The POST creates a new resource in the dataset.
And the PUT updates a resource in the dataset, for example the therm most associated with them.
These last 3 can only be succesfully handled by an administrator.

For songs, we also allow GET, POST, DELETE and PUT.
The GET methods are again safe, and only display the appropriate data.
The POST will be used by an administrator if a new song is released and adds that resource to the dataset.
The PUT can be used by administrators to update for example the hotness of a song.
We didn't implement DELETE for songs because songs can't really be removed once published.

