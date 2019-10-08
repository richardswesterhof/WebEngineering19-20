FORMAT: 1A
HOST: http://ourserver.com/


# Web Engineering API Design

This API provides insights to the Million Song Dataset made available by the CORGIS dataset project 
and can be found at https://think.cs.vt.edu/corgis/csv/music/.

Web Engineering project 19-20 by Cornelis Zwart and Richard Westerhof.

# Contents

## [Artists Collection](#artistsCollection)

+ [Get All Artists](#getArtists)
+ [Get Artist](#getArtist)
+ [Get Artist Statistics](#getArtistStats)
+ [Get Artist Hotness](#getArtistHotness)
+ [Add Artist](#addArtist)
+ [Remove Artist](#removeArtist)
+ [Update Artist](#updateArtist)


## [Songs Collection](#songsCollection)
+ [Get All Song](#getSongs)
+ [Get Information About Song By Id](#getSong)
+ [Get Songs By Popularity](#songsByPopularity)
+ [Add Song](#addSong)
+ [Update Song](#updateSong)


## [Supported Methods](#methods)


## Remarks
Note that every request that returns any data in the body of the response has an optional header for the representation of that data. It looks as follows

+ representation (="json" OR "csv")
    
This header can have the value "json" or "csv", it will default to json if an invalid value is given, or if the header is left out completely.


## <a name="artistsCollection"></a> Artists Collection [/artists]

### <a name="getArtists"></a> Get All Artists [GET ?name={artistName}&genre={genre}]



+ Parameters
    + name (string) - name of the artist
    + genre (string) - genre of artist

+ Response 200 (application/json)

        [
            {
                "id" : "ARD7TVE1187B99BFB1",
                "name": "Jack",
                "term" : "Country"
                "pageLink" : "/ARD7TVE1187B99BFB1"
            },
            {
                "id" : "ABCDEFGHIJK1234567",
                "name": "Bob",
                "term" : "Pop"
                "pageLink" : "/ABCDEFGHIJK1234567"
            },
            etc.
        ]
        
+ Response 200 (text/csv)

        "id", "name", "term", "pageLink"
        "ARD7TVE1187B99BFB1", "Jack", "Country", "/ARD7TVE1187B99BFB1"
        "ABCDEFGHIJK1234567", "Bob", "Pop", "/ABCDEFGHIJK1234567"
        etc.
        
        
        
        
        

        
### <a name="getArtist"></a> Get Artist [GET /{artistId}]


+ Parameters
    + artistId (string) - id of the artist

+ Response 200 (application/json)

        {
            "id" : "ARD7TVE1187B99BFB1",
            "name": "Jack",
            "term" : "Country",
            "linkToArtistsSongs" : "?artistId=ARD7TVE1187B99BFB1&artistName=&year=&term="
        }
        
+ Response 200 (text/csv)

        "id", "name", "term", "linkToArtistsSongs"
        "ARD7TVE1187B99BFB1", "Jack", "Country", "?artistId=ARD7TVE1187B99BFB1&artistName=&year=&term="
        
        
        
        
        

### <a name="getArtistStats"></a> Get Artist Statistics [GET /{artistId}/statistics?year={year}]


+ Parameters
    + artistId (string) - ID of the artist
    + year (number) - The year of release
    
+ Response 200 (application/json)

        {
                "mean" : 0.5,
                "median" : 0.5,
                "standard_deviation" : 0.5
        }
        
+ Response 200 (text/csv)

        "mean", "median", "standard_deviation"
        "0.5", "0.5", "0.5"
        
        
        
        
        
        

### <a name="getArtistHotness"></a> Get Artist Hotness [GET /hotness?pageSize={pageSize}&pageRank={pageRank}]


+ Parameters
    + pageSize (number) - number of artists per page
    + pageRank (number) - the number of page queried
    
+ Response 200 (application/json)

            [
                {
                    "id" : "ARD7TVE1187B99BFB1",
                    "name": "Jack",
                    "term" : "Country"
                    "pageLink" : "/ARD7TVE1187B99BFB1"
                },
                {
                    "id" : "ABCDEFGHIJK1234567",
                    "name": "Bob",
                    "term" : "Pop"
                    "pageLink" : "/ABCDEFGHIJK1234567"
                },
                etc.
            ]
        
        
+ Response 200 (text/csv)

        "id", "name", "term", "pageLink"
        "ARD7TVE1187B99BFB1", "Jack", "Country", "/ARD7TVE1187B99BFB1"
        "ABCDEFGHIJK1234567", "Bob", "Pop", "/ABCDEFGHIJK1234567"
        etc.
        
        
        
        




### <a name="addArtist"></a> Add Artist [POST]


+ Request (application/json)

        {
            "name": "Jack",
            "terms": "Country"
        }

+ Response 201 (application/json)

    + Headers

            Location: /artists/ARD7TVE1187B99BFB1

    + Body

            {
                "name": "Jack",
                "id": "ARD7TVE1187B99BFB1",
                "terms": "Country"
            }
            
            
            
            
+ Response 201 (text/csv)

    + Headers 
    
            Location: /artists/ARD7TVE1187B99BFB1
            
    + Body
    
            "name", "id", "terms"
            "Jack", "ARD7TVE1187B99BFB1", "Country"








### <a name="removeArtist"></a> Remove Artist [DELETE/{artistId}]

+ Parameters
    + artistId (string) - ID of the artist

+ Response 204

+ Response 403









### <a name="updateArtist"></a> Update Artist [PUT /{artistId}]

        
+ Response 200 (application/json)

        {
            "name": "Jack",
            "id": "ARD7TVE1187B99BFB1",
            "terms": "Country"
        }
        
        
+ Response 200 (text/csv)

        "name", "id", "terms"
        "Jack", "ARD7TVE1187B99BFB1", "Country"
        
        
+ Response 403



## <a name="songsCollection"></a> Songs Collection [/songs]

### <a name="getSongs"></a> Get All Songs [GET ?artistId={artistId}&artistName={artistName}&year={year}&term={artistTerm}]


+ Parameters 
    + artistId (string) - id of the artist
    + artistName (string) - name of the artist
    + year (number) - The year of release
    + artistTerm (string) - The genre of the artist

+ Response 200 (application/json)

        [
            {
                "title": "Cowboy Jim",
                "artistsName" : "Jack",
                "duration": 420,
                "year": 2012
                "linkToSong" : "/SOMZWCG12A8C13C480"
                "linkToArtist" : "ourserver.com/artists/ARD7TVE1187B99BFB1"
            },
            {
                "title": "Red Roses",
                "artistsName" : "Bob",
                "duration": 666,
                "year": 2008
                "linkToSong" : "/QWERTYUIOP12345678"
                "linkToArtist" : "ourserver.com/artists/ABCDEFGHIJK1234567"
            },
            etc.
        ]


+ Response 200 (text/csv)

        "title", "artistsName", "duration", "year", "linkToSong", "linkToArtist"
        "Cowboy Jim", "Jack", "420", "2012", "/SOMZWCG12A8C13C480", "ourserver.com/artists/ARD7TVE1187B99BFB1"
        "Red Roses", "Bob", "666", "2008", "/QWERTYUIOP12345678", "ourserver.com/artists/ABCDEFGHIJK1234567"
        etc.








### <a name="getSong"></a> Get Information About Song By Id [GET /{songId}]


+ Parameters 
    + songId (string) - ID of the song

+ Response 200 (application/json)

        {
            "title": "Cowboy Jim",
            "artitsName" : "Jack",
            "duration": 420,
            "year": 2012,
            "linkToSong" : "/SOMZWCG12A8C13C480"
            "linkToArtist" : "ourserver.com/artists/ARD7TVE1187B99BFB1"           
        }
        
        
+ Response 200 (text/csv)

        "title", "artistsName", "duration", "year", "linkToSong", "linkToArtist"
        "Cowboy Jim", "Jack", "420", "2012", "/SOMZWCG12A8C13C480", "ourserver.com/artists/ARD7TVE1187B99BFB1"
        
        
        
        
        
        
        
        
        
### <a name="songsByPopularity"></a> Get songs by popularity [GET /popularity?year={year}&pageSize={pageSize}&pageRank={pageRank}]


+ Parameters 
    + year (number) - year of the song
    + pageSize (number) - number of artists per page
    + pageRank (number) - the number of page queried

+ Response 200 (application/json)

        [
            {
                "title": "Cowboy Jim",
                "artitsName" : "Jack",
                "duration": 420,
                "year": 2012,
                "linkToSong" : "/SOMZWCG12A8C13C480"
                "linkToArtist" : "ourserver.com/artists/ARD7TVE1187B99BFB1"           
            },
            {
                "title": "Red Roses",
                "artistsName" : "Bob",
                "duration": 666,
                "year": 2008
                "linkToSong" : "/QWERTYUIOP12345678"
                "linkToArtist" : "ourserver.com/artists/ABCDEFGHIJK1234567"
            },
            etc.
        ]
        
+ Response 200 (text/csv)

        "title", "artistsName", "duration", "year", "linkToSong", "linkToArtist"
        "Cowboy Jim", "Jack", "420", "2012", "/SOMZWCG12A8C13C480", "ourserver.com/artists/ARD7TVE1187B99BFB1"
        "Red Roses", "Bob", "666", "2008", "/QWERTYUIOP12345678", "ourserver.com/artists/ABCDEFGHIJK1234567"
        etc.
        
        
        
        
        
        
        
        
        
 ### <a name="addSong"></a> Add Song [POST]


+ Request (application/json)

        {
            "title": "Cowboy Jim",
            "artistsName": "Jack",
            "duration": 420,
            "year": 2012
        }

+ Response 201 (application/json)

    + Headers

            Location: /songs/SOMZWCG12A8C13C480

    + Body

            {
                "title": "Cowboy Jim",
                "artistsName": "Jack",
                "duration": 420,
                "year": 2012,
                "linkToSong" : "/SOMZWCG12A8C13C480"
            }
            
            
            
            
+ Response 201 (text/csv)

    + Headers 
    
            Location: /songs/SOMZWCG12A8C13C480
            
    + Body
    
            "title", "artistsName", "duration", "year", "linkToSong"
            "Cowboy Jim", "Jack", "420", "2012", "/SOMZWCG12A8C13C480"







### <a name="updateSong"></a> Update Song [PUT /{songId}]

        
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
        
        
+ Response 403


       



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

