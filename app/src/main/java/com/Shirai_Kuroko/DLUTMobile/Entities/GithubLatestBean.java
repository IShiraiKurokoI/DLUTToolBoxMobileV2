package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GithubLatestBean {
    @JsonProperty("url")
    private String url;
    @JsonProperty("assets_url")
    private String assets_url;
    @JsonProperty("upload_url")
    private String upload_url;
    @JsonProperty("html_url")
    private String html_url;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("author")
    private AuthorDTO author;
    @JsonProperty("node_id")
    private String node_id;
    @JsonProperty("tag_name")
    public String tag_name;
    @JsonProperty("target_commitish")
    private String target_commitish;
    @JsonProperty("name")
    public String name;
    @JsonProperty("draft")
    private Boolean draft;
    @JsonProperty("prerelease")
    private Boolean prerelease;
    @JsonProperty("created_at")
    private String created_at;
    @JsonProperty("published_at")
    public String published_at;
    @JsonProperty("assets")
    public List<AssetsDTO> assets;
    @JsonProperty("tarball_url")
    private String tarball_url;
    @JsonProperty("zipball_url")
    private String zipball_url;
    @JsonProperty("body")
    public String body;

    @NoArgsConstructor
    @Data
    public static class AuthorDTO {
        @JsonProperty("login")
        private String login;
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("node_id")
        private String node_id;
        @JsonProperty("avatar_url")
        private String avatar_url;
        @JsonProperty("gravatar_id")
        private String gravatar_id;
        @JsonProperty("url")
        private String url;
        @JsonProperty("html_url")
        private String html_url;
        @JsonProperty("followers_url")
        private String followers_url;
        @JsonProperty("following_url")
        private String following_url;
        @JsonProperty("gists_url")
        private String gists_url;
        @JsonProperty("starred_url")
        private String starred_url;
        @JsonProperty("subscriptions_url")
        private String subscriptions_url;
        @JsonProperty("organizations_url")
        private String organizations_url;
        @JsonProperty("repos_url")
        private String repos_Url;
        @JsonProperty("events_url")
        private String events_url;
        @JsonProperty("received_events_url")
        private String received_events_url;
        @JsonProperty("type")
        private String type;
        @JsonProperty("site_admin")
        private Boolean site_admin;
    }

    @NoArgsConstructor
    @Data
    public static class AssetsDTO {
        @JsonProperty("url")
        private String url;
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("node_id")
        private String nodeId;
        @JsonProperty("name")
        private String name;
        @JsonProperty("label")
        private Object label;
        @JsonProperty("uploader")
        private UploaderDTO uploader;
        @JsonProperty("content_type")
        private String content_type;
        @JsonProperty("state")
        private String state;
        @JsonProperty("size")
        private Integer size;
        @JsonProperty("download_count")
        private Integer download_count;
        @JsonProperty("created_at")
        private String created_at;
        @JsonProperty("updated_at")
        private String updated_at;
        @JsonProperty("browser_download_url")
        public String browser_download_url;

        @NoArgsConstructor
        @Data
        public static class UploaderDTO {
            @JsonProperty("login")
            private String login;
            @JsonProperty("id")
            private Integer id;
            @JsonProperty("node_id")
            private String nodeId;
            @JsonProperty("avatar_url")
            private String avatarUrl;
            @JsonProperty("gravatar_id")
            private String gravatarId;
            @JsonProperty("url")
            private String url;
            @JsonProperty("html_url")
            private String htmlUrl;
            @JsonProperty("followers_url")
            private String followersUrl;
            @JsonProperty("following_url")
            private String followingUrl;
            @JsonProperty("gists_url")
            private String gistsUrl;
            @JsonProperty("starred_url")
            private String starredUrl;
            @JsonProperty("subscriptions_url")
            private String subscriptionsUrl;
            @JsonProperty("organizations_url")
            private String organizationsUrl;
            @JsonProperty("repos_url")
            private String reposUrl;
            @JsonProperty("events_url")
            private String eventsUrl;
            @JsonProperty("received_events_url")
            private String receivedEventsUrl;
            @JsonProperty("type")
            private String type;
            @JsonProperty("site_admin")
            private Boolean siteAdmin;
        }

        public String getBrowserDownloadUrl() {
            return browser_download_url;
        }

        public Integer getSize() {
            return size;
        }
    }

    public String getBody() {
        return body;
    }

    public String getTagName() {
        return tag_name;
    }

    public String getName() {
        return name;
    }

    public String getPublishedAt() {
        return published_at;
    }

    public List<AssetsDTO> getAssets() {
        return assets;
    }
}
