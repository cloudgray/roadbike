import axios from "axios";

const api = axios.create({
  baseURL: "http://127.0.0.1:8080/",
  headers: {'Access-Control-Allow-Origin': '*'},
});

export const roadbikeApi = {
  produceCrankset: () => api.get("shimano/crankset"),
  nowPlaying: () => api.get("movie/now_playing"),
  upcoming: () => api.get("movie/upcoming"),
  popular: () => api.get("movie/popular"),
  movieDetail: id =>
    api.get(`movie/${id}`, {
      params: {
        append_to_response: "videos"
      }
    }),
  search: term =>
    api.get("search/movie", {
      params: {
        query: encodeURIComponent(term)
      }
    })
};
