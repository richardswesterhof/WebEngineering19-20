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
+ [Add Artist](#addArtist)
+ [Remove Artist](#removeArtist)
+ [Update Artist](#updateArtist)


## [Songs Collection](#songsCollection)
+ [Get All Song](#getSongs)
+ [Get Information About Song By Id](#getSong)

## <a name="artistsCollection"></a> Artists Collection [/artists]

### <a name="getArtists"></a> Get All Artists [GET /all/name={artistName}&genre={genre}]
+ Parameters
    + name (string) - name of the artist
    + genre (string) - genre of artist

+ Response 200 (application/json)

        [
            {
                "id" : "ARD7TVE1187B99BFB1",
                "name": "Sans",
                "term" : "Country"
                "pageLink" : "/ARD7TVE1187B99BFB1"
            }
        ]


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

### <a name="getArtistPopularity"></a> Get Artist popularity [GET /popularity?pageSize={pageSize}&pageRank={?pageRank}]

+ Parameters
    + pageSize (number) - number of artists per page
    + pageRank (number) - the number of page queried
    
+ Response 200 (application/json)

        {
                [
                    {
                        "id" : "ARD7TVE1187B99BFB1",
                        "name": "Sans",
                        "term" : "Country"
                        "pageLink" : "/ARD7TVE1187B99BFB1"
                    }
                ]
        }


### <a name="addArtist"></a> Add Artist [POST/add]

+ Request (application/json)

        {
            "name": "Joe Mama",
            "terms": "classical"
        }

+ Response 201 (application/json)

    + Headers

            Location: /artists/69PP

    + Body

            {
                "name": "Joe Mama",
                "id": "69PP",
                "terms": "classical"
            }



### <a name="removeArtist"></a> Remove Artist [DELETE/{artistId}]

+ Parameters
    + artistId (string) - ID of the artist

+ Response 204

+ Response 403


### <a name="updateArtist"></a> Update Artist [POST/{artistId}]

+ Parameters
        + artistId (string) - ID of the artist
        
+ Response 200

        {
             UPDATED ARTIST DETAILS
        }
        
+ Response 403



## <a name="songsCollection"></a> Songs Collection [/songs]

### <a name="getSongs"></a> Get All Songs [GET/{?artistId}{?year}{?artistTerm}]

+ Parameters 
    + artistId (string) - ID of the artist
    + year (number) - The year of release
    + artistTerm (string) - The genre of the artist

+ Response 200 (application/json)

        [
            {
                SONG DATA, EG
                "title": "Baby I'm Yours",
                "length": 420,
            }
        ]


### <a name="getSong"></a> Get Information About Song By Id [GET/{songId}]

+ Parameters 
    + songId (string) - ID of the song

+ Response 200 (application/json)

        {
            SONG DATA, EG
            "title": "AllStar",
            "length": 420,
        }
