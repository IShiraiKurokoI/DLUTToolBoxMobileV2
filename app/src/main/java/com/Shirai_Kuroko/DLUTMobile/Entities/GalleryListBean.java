package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GalleryListBean {

    @JsonProperty("data")
    private DataDTO data;
    @JsonProperty("ret")
    private Integer ret;
    @JsonProperty("errcode")
    private Integer errcode;
    @JsonProperty("errmsg")
    private String errmsg;

    public DataDTO getData() {
        return data;
    }

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("carousel_gallery")
        private List<CarouselGalleryDTO> carousel_gallery;

        public List<CarouselGalleryDTO> getCarousel_gallery() {
            return carousel_gallery;
        }

        @NoArgsConstructor
        @Data
        public static class CarouselGalleryDTO {
            @JsonProperty("id")
            private String id;
            @JsonProperty("pic_uri")
            private String pic_uri;
            @JsonProperty("url")
            private String url;
            @JsonProperty("title")
            private String title;
            @JsonProperty("type")
            private String type;
            @JsonProperty("status")
            private String status;
            @JsonProperty("num")
            private String num;

            public String getUrl() {
                return url;
            }

            public String getPic_uri() {
                return pic_uri;
            }
        }
    }
}
