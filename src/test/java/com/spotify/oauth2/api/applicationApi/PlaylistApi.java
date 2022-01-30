package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import io.restassured.response.Response;

public class PlaylistApi {

    static String userId = "31uxnqcuy7hrnntpso5yaoe56uzy";
    static String access_Token = "BQCPJRbaksYUgOcVL4TcQdO3Uw5gukdaG_W30SujnnahjLGMf9rwm9P36i8NZbURxufHSM0kMUZYr5c7Zi3-MxB8Owzy1BrZDLKNsZCPjywmAzAcl9_rjRWoFWQ1MzTfAt2huInXsRuDHU4d5sY4iw-KPWnmv6GkmSyY2_bAtcomIfWzvR0FqO8wwZUSRXyyW5QBw58OhemmKZZIKdQ1OSqLkYKanvlPohNRwaXmYFyL5GGq";

    public static Response post(Playlist requestPlaylist) {
        return RestResource.post(requestPlaylist,access_Token,"/users/" + userId + "/playlists");
    }

    public static Response post(Playlist requestPlaylist, String token) {
        return RestResource.post(requestPlaylist,token,"/users/" + userId + "/playlists");
    }

    public static Response get(String playlistId){
        return RestResource.get(access_Token, "/playlists/"+playlistId);
    }

    public static Response put(Playlist requestPlaylist, String playlistId)
    {
        return RestResource.put(requestPlaylist,access_Token,"/playlists/" + playlistId);
    }
}
