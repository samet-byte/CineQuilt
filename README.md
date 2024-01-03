# Cine Quilt App

Web Design and Applications Term Project Backend

- Java (pinch of Kotlin :)
- Spring Web
- Spring Security
- JPA



## Getting Started

### Prerequisites

- Java 8 or higher (Java 17 is recommended)
- Gradle
- PostgreSQL

### Installing

1. Clone the repository
```bash
git clone https://github.com/samet-byte/CineQuiltAppBackend.git
```

2. Install dependencies
```bash
gradle build
```

3. Create a PostgreSQL database and follow the instructions in [`DB_INSTRUCTIONS.md`](DB_INSTRUCTIONS.md)

```bash
create database cine_demo_db2
```

4. Create a `application-key.yml` file in `src/main/resources` and add the following lines

```yml
# application-key.yml

## for film buff
gpt:
  api-key: *
key:
  mail-username: example@gmail.com
  mail-password: pass word 1234 5678

#for PostgreSQL Google Cloud
g_cloud_psql:
  url: jdbc:postgresql://global_ip:5432/cine_demo_db2
  username: *
  password: *
```

5. Run the application

```bash
gradle bootRun
```

## Built With

- [Spring](https://spring.io/) - The web framework used
- [Gradle](https://gradle.org/) - Dependency Management
- [Kotlin](https://kotlinlang.org/) - Programming Language
- [PostgreSQL](https://www.postgresql.org/) - Database
- [IntelliJ IDEA](https://www.jetbrains.com/idea/) - IDE
- [Postman](https://www.postman.com/) - API Development Environment
- [Git](https://git-scm.com/) - Version Control System
- [GitHub](https://github.com/samet-byte/cinequilt-app-v2) - Code Hosting Platform

## Authors
- [Samet Bayat](https://sametb.com/)
- [Ramazan Alper]()

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

# API Documentation

## Base URL
`/api/v1`

## Authentication
- **Endpoint:**
    - `/auth`
        - **Logout:**
            - `POST api/v1/auth/logout`

## Demo
- **Management:**
    - `GET api/v1/management/**`

## User Operations
- **Users:**
    - `GET api/v1/users`

## Metadata Operations
- **Metadatas:**
    - `GET api/v1/metadatas`
    - **Search:**
        - `GET api/v1/metadatas/search`

## Favorites
- **Favs:**
    - `GET api/v1/favs`

## Series Operations
- **Series:**
    - `GET api/v1/series`
- **Episode:**
    - `GET api/v1/series/episode`

## API Operations
- **ID:**
    - `GET ../{id}`
- **IP:**
    - `GET api/v1/ip`




> **Note:** Movie and TV Show samples are taken from [TMDB](https://www.themoviedb.org/)
> and saved to the database with the help of `Python` scripts.
> ```python
> import requests
> 
> bearer_token = 'TOKEN_GOES_HERE'
> # content_id = 641
> # query = 'Sherlock'
> content_id = input("Enter content id: ")
> query = input("Enter query: ")
> api_key = 'e7ce7a37cf9ed48b92b0d582160622a7'
> 
> def post_episode(episode_number, episode_name, episode_description, season_number):
>     # Replace the following placeholder values with your actual endpoint and request body
>     endpoint = "http://localhost:8080/api/v1/series"  # Replace with your API endpoint
>     headers = {
>         'Authorization': f'Bearer {bearer_token}',
>         'Content-Type': 'application/json',
>         'User-Agent': 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36'
>     }
> 
>     # Replace the following placeholder values with your actual request body
>     request_body = {
>         'metadataId': content_id,
>         'season': season_number,
>         'episode': episode_number,
>         'title': episode_name,
>         'description': episode_description,
>         'videoUrl': None
>     }
> 
>     # Make the POST request
>     response = requests.post(endpoint, headers=headers, json=request_body, verify=False, timeout=5)
> # 201
>     if response.status_code == 200:
>         print(f"Successfully posted episode {episode_number} of season {season_number}")
>     else:
>         print(f"Failed to post episode {episode_number} of season {season_number}. Status code: {response.status_code}")
> 
> 
> def get_seasons(api_key):
>     # Set up the base URL for the TMDb API
>     base_url = "https://api.themoviedb.org/3"
> 
>     search_url = f"{base_url}/search/tv"
>     search_params = {
>         'api_key': api_key,
>         'query': query
>     }
> 
>     # Make the request to search for the TV show
>     response = requests.get(search_url, params=search_params)
>     show_data = response.json()
> 
>     show_id = show_data['results'][0]['id']
> 
>     seasons_url = f"{base_url}/tv/{show_id}"
>     seasons_params = {
>         'api_key': api_key
>     }
> 
>     # Make the request to get information about all seasons
>     response = requests.get(seasons_url, params=seasons_params)
>     show_info = response.json()
> 
>     # Print information about each season and its episodes
>     for season in show_info['seasons']:
>         season_number = season['season_number']
>         print(f"\nSeason {season_number} - {season['name']}")
> 
>         # Get the list of episodes for the current season
>         episodes_url = f"{base_url}/tv/{show_id}/season/{season_number}"
>         episodes_params = {
>             'api_key': api_key
>         }
> 
>         # Make the request to get the list of episodes
>         response = requests.get(episodes_url, params=episodes_params)
>         episodes_data = response.json()
> 
>         # Print information about each episode in the season
>         for episode in episodes_data['episodes']:
>             print(f"\tEpisode {episode['episode_number']}: {episode['name']}")
>             print(f"\t\tDescription: {episode['overview']}\n")
>             
>             episode_number = episode['episode_number']
>             episode_name = episode['name']
>             episode_description = episode['overview']
>             post_episode(episode_number, episode_name, episode_description, season_number)
>             # input("Continue?")
> 
> if __name__ == "__main__":
>     get_seasons(api_key) 
> ```