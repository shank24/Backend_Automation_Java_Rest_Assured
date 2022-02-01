package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.util.ConfigLoader;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.Route.PLAYLISTS;
import static com.spotify.oauth2.api.Route.USERS;
import static com.spotify.oauth2.api.TokenManager.getToken;

public class PlaylistApi {


    public static Response post(Playlist requestPlaylist) {
        return RestResource.post(requestPlaylist,getToken(),USERS +"/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS);
    }

    public static Response post(Playlist requestPlaylist, String token) {
        return RestResource.post(requestPlaylist,token,USERS +"/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS);
    }

    public static Response get(String playlistId){
        return RestResource.get(getToken(), PLAYLISTS +"/" + playlistId);
    }

    public static Response put(Playlist requestPlaylist, String playlistId) {
        return RestResource.put(requestPlaylist,getToken(),PLAYLISTS + "/" + playlistId);
    }
}
