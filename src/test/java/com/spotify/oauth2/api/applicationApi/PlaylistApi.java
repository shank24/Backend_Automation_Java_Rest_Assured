package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.Route.PLAYLISTS;
import static com.spotify.oauth2.api.Route.USERS;
import static com.spotify.oauth2.api.TokenManager.getToken;

public class PlaylistApi {

    static String userId = "31uxnqcuy7hrnntpso5yaoe56uzy";

    public static Response post(Playlist requestPlaylist) {
        return RestResource.post(requestPlaylist,getToken(),USERS +"/" + userId + PLAYLISTS);
    }

    public static Response post(Playlist requestPlaylist, String token) {
        return RestResource.post(requestPlaylist,token,USERS +"/" + userId + PLAYLISTS);
    }

    public static Response get(String playlistId){
        return RestResource.get(getToken(), PLAYLISTS +"/" + playlistId);
    }

    public static Response put(Playlist requestPlaylist, String playlistId) {
        return RestResource.put(requestPlaylist,getToken(),PLAYLISTS + "/" + playlistId);
    }
}
