FORMAT: 1A
HOST: http://ourserver.com/


# Web Engineering API Design

This API provides insights to the Million Song Dataset made available by the CORGIS dataset project 
and can be found at https://think.cs.vt.edu/corgis/csv/music/.

Web Engineering project 19-20 by Cornelis Zwart and Richard Westerhof.

# Contents

## [Artists Collection](#artistsCollection)

+ [Get All Artists](#getArtists)
+ [Get Artist Statistics](#getArtistStats)
+ [Get Artist Hotness](#getArtistHotness)
+ [Add Artist](#addArtist)
+ [Remove Artist](#removeArtist)
+ [Update Artist](#updateArtist)


## [Songs Collection](#songsCollection)
+ [Get All Song](#getSongs)
+ [Get Information About Song By Id](#getSong)
+ [Get Songs By Popularity](#songsByPopularity)

## <a name="artistsCollection"></a> Artists Collection [/artists]

### <a name="getArtists"></a> Get All Artists [GET ?name={artistName}&genre={genre}]

+ Headers 
    + representation (=json OR csv) - specifies the desired representation of the repsonse, if none is specified or an invalid value is given, json is used as the default


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
            }
        ]
        
+ Response 200 (text/csv)

        "id", "name", "term", "pageLink"
        "ARD7TVE1187B99BFB1", "Jack", "Country", "/ARD7TVE1187B99BFB1"
        
        
        
        
        

        
### <a name="getArtist"></a> Get Artist [GET /artistId={artistId}]

+ Headers 
    + representation (=json OR csv) - specifies the desired representation of the repsonse, if none is specified or an invalid value is given, json is used as the default

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

+ Headers 
    + representation (=json OR csv) - specifies the desired representation of the repsonse, if none is specified or an invalid value is given, json is used as the default

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
        
        
        
        
        
        

### <a name="getArtistHotness"></a> Get Artist Hotness [GET /hotness?pageSize={pageSize}&pageRank={?pageRank}]

+ Headers 
    + representation (=json OR csv) - specifies the desired representation of the repsonse, if none is specified or an invalid value is given, json is used as the default

+ Parameters
    + pageSize (number) - number of artists per page
    + pageRank (number) - the number of page queried
    
+ Response 200 (application/json)

        {
                [
                    {
                        "id" : "ARD7TVE1187B99BFB1",
                        "name": "Jack",
                        "term" : "Country"
                        "pageLink" : "/ARD7TVE1187B99BFB1"
                    }
                ]
        }
        
        
+ Response 200 (text/csv)

        "id", "name", "term", "pageLink"
        "ARD7TVE1187B99BFB1", "Jack", "Country", "/ARD7TVE1187B99BFB1"
        
        
        
        




### <a name="addArtist"></a> Add Artist [POST/add]

+ Headers 
    + representation (=json OR csv) - specifies the desired representation of the repsonse, if none is specified or an invalid value is given, json is used as the default

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









### <a name="updateArtist"></a> Update Artist [POST/{artistId}]

+ Headers 
    + representation (=json OR csv) - specifies the desired representation of the repsonse, if none is specified or an invalid value is given, json is used as the default

        
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

+ Headers 
    + representation (=json OR csv) - specifies the desired representation of the repsonse, if none is specified or an invalid value is given, json is used as the default

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
            }
        ]


+ Response 200 (text/csv)

        "title", "artistsName", "duration", "year", "linkToSong", "linkToArtist"
        "Cowboy Jim", "Jack", "420", "2012", "/SOMZWCG12A8C13C480", "ourserver.com/artists/ARD7TVE1187B99BFB1"









### <a name="getSong"></a> Get Information About Song By Id [GET /{songId}]

+ Headers 
    + representation (=json OR csv) - specifies the desired representation of the repsonse, if none is specified or an invalid value is given, json is used as the default

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
        
        
        
        
        
        
        
        
        
### <a name="songsByPopularity"></a> Get songs by popularity [GET /popularity?year={year}&pageSize={pageSize}&pageRank={?pageRank}]

+ Headers 
    + representation (=json OR csv) - specifies the desired representation of the repsonse, if none is specified or an invalid value is given, json is used as the default

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
            }
        ]
        
+ Response 200 (text/csv)

        "title", "artistsName", "duration", "year", "linkToSong", "linkToArtist"
        "Cowboy Jim", "Jack", "420", "2012", "/SOMZWCG12A8C13C480", "ourserver.com/artists/ARD7TVE1187B99BFB1"
        
        
        
        
        
        
        
        
        
        

