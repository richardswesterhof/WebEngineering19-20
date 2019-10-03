FORMAT: 1A
HOST: http://ourserver.com/


# Web Engineering API Design

This API provides insights to the Million Song Dataset made available by the CORGIS dataset project 
and can be found at https://think.cs.vt.edu/corgis/csv/music/.

Web Engineering project 19-20 by Cornelis Zwart and Richard Westerhof.



## Artists Collection [/artists]

### Get All Artists [GET]

+ Response 200 (application/json)

        [
            {
                ARTIST DATA, EG
                "name": "Sans",
                "songs": [
                    {
                        SONG DATA, EG
                        "title": "MEGALOVANIA",
                        "length" : "420",
                    }
                ],
            }
            ETC.
        ]


### Get Artist Statistics [GET/{artistId}{?year}]

+ Parameters
    + year (number) - The year of release
    
+ Response 200 (application/json)

        [
            SONGS
        ]

### Add Artist [POST/add]

+ Request (application/json)

        {
            "name": "Joe Mama",
            "terms": "classical",
        }

+ Response 201 (application/json)

    + Headers

            Location: /artists/69PP

    + Body

            {
                "name": "Joe Mama",
                "id": "69PP",
                "published_at": "2019-10-02T22:16:51.620Z",
                "terms": "classical",
            }



### Remove Artist [DELETE/{artistId}]

+ Parameters
    + artistId (string) - ID of the artist

+ Response 204

+ Response 403



## Songs Collection [/songs]

### Get All Songs [GET/{?artistId}{?year}{?artistTerm}]

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


### Get Information About Song By Id [GET/{songId}]

+ Parameters 
    + songId (string) - ID of the song

+ Response 200 (application/json)

        {
            SONG DATA, EG
            "title": "AllStar",
            "length": 420,
        }
