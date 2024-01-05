# POSTGRESQL DATABASE INSTRUCTIONS

## Create a database

```sql
create database cine_demo_db2
```

No longer needed!
## !! Function is Deprecated From Now On You Do Not Need the Function Below For Sorting. It's Handling by `PagingAndSortingRepository`. 
## ❌ Create a `get_by_column_and_value_sort_by_column_and_order` function in `cine_demo_db2` and add the following lines

```sql
CREATE OR REPLACE FUNCTION public.get_by_column_and_value_sort_by_column_and_order(
    _COLUMN varchar,
    _VALUE  varchar,
    _BY varchar DEFAULT 'title',
    _ORDER  varchar DEFAULT 'ASC' -- Default order is ASC
)
RETURNS TABLE (
    id               integer,
    create_date      timestamp(6),
    created_by       integer,
    director         varchar(255),
    duration         integer,
    last_modified    timestamp(6),
    last_modified_by integer,
    poster_url       varchar(255),
    release_year     integer,
    soundtrack_url   varchar(255),
    title            varchar(255),
    trailer_url      varchar(255),
    video_url        varchar(255),
    description      varchar(1000),
    genre            varchar(255),
    type             varchar(255),
    episode_number   integer,
    season_number    integer
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY EXECUTE FORMAT(
        'SELECT * FROM metadatas %s ORDER BY %I %s',
        CASE WHEN
            _COLUMN IS NOT NULL AND
            _VALUE IS NOT NULL AND
            _VALUE::text IN ('MOVIE', 'TV_SHOW')
        THEN
            FORMAT('WHERE %I = $1', quote_ident(_COLUMN))   
        ELSE
            ''
        END,
        quote_ident(_BY),
        _ORDER
    )
    USING _VALUE;
END;
$$;
```


