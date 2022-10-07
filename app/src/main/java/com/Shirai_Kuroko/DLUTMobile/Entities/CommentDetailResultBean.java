package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName CommentDetailResultBean.java
 * @Description TODO
 * @createTime 2022年10月06日 19:15
 */
@NoArgsConstructor
@Data
public class CommentDetailResultBean {

    @JsonProperty("data")
    private DataDTO data;
    @JsonProperty("ret")
    private Integer ret;
    @JsonProperty("errcode")
    private Integer errcode;
    @JsonProperty("errmsg")
    private String errmsg;

    public Integer getRet() {
        return ret;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public DataDTO getData() {
        return data;
    }

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("iscomment")
        private Integer iscomment;
        @JsonProperty("total")
        private String total;
        @JsonProperty("list_data")
        private List<ListDataDTO> list_data;
        @JsonProperty("statistics")
        private StatisticsDTO statistics;

        public List<ListDataDTO> getList_data() {
            return list_data;
        }

        public StatisticsDTO getStatistics() {
            return statistics;
        }

        public String getTotal() {
            return total;
        }

        public Integer getIscomment() {
            return iscomment;
        }

        @NoArgsConstructor
        @Data
        public static class StatisticsDTO {
            @JsonProperty("average")
            private Double average;
            @JsonProperty("score")
            private ScoreDTO score;
            @JsonProperty("total")
            private Integer total;

            public ScoreDTO getScore() {
                return score;
            }

            public Double getAverage() {
                return average;
            }

            public Integer getTotal() {
                return total;
            }

            @NoArgsConstructor
            @Data
            public static class ScoreDTO {
                @JsonProperty("1")
                private Integer $1;
                @JsonProperty("2")
                private Integer $2;
                @JsonProperty("3")
                private Integer $3;
                @JsonProperty("4")
                private Integer $4;
                @JsonProperty("5")
                private Integer $5;

                public Integer get$1() {
                    return $1;
                }

                public Integer get$2() {
                    return $2;
                }

                public Integer get$3() {
                    return $3;
                }

                public Integer get$4() {
                    return $4;
                }

                public Integer get$5() {
                    return $5;
                }
            }
        }

        @NoArgsConstructor
        @Data
        public static class ListDataDTO {
            @JsonProperty("jid")
            private String jid;
            @JsonProperty("name")
            private String name;
            @JsonProperty("organization")
            private String organization;
            @JsonProperty("comment")
            private String comment;
            @JsonProperty("score")
            private Integer score;
            @JsonProperty("add_time")
            private String add_time;
            @JsonProperty("add_time_format")
            private String add_time_format;
            @JsonProperty("user_info")
            private UserInfoDTO user_info;

            public String getJid() {
                return jid;
            }

            public void setJid(String jid) {
                this.jid = jid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOrganization() {
                return organization;
            }

            public void setOrganization(String organization) {
                this.organization = organization;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public Integer getScore() {
                return score;
            }

            public void setScore(Integer score) {
                this.score = score;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getAdd_time_format() {
                return add_time_format;
            }

            public void setAdd_time_format(String add_time_format) {
                this.add_time_format = add_time_format;
            }

            public UserInfoDTO getUser_info() {
                return user_info;
            }

            public void setUser_info(UserInfoDTO user_info) {
                this.user_info = user_info;
            }

            @NoArgsConstructor
            @Data
            public static class UserInfoDTO {
                @JsonProperty("user_id")
                private String user_id;
                @JsonProperty("identity")
                private String identity;
                @JsonProperty("sex")
                private String sex;
                @JsonProperty("name")
                private String name;
                @JsonProperty("student_number")
                private String student_number;
                @JsonProperty("head")
                private String head;
                @JsonProperty("mood_words")
                private String mood_words;
                @JsonProperty("nick_name")
                private String nick_name;

                public String getUser_id() {
                    return user_id;
                }

                public String getIdentity() {
                    return identity;
                }

                public String getSex() {
                    return sex;
                }

                public String getName() {
                    return name;
                }

                public String getStudent_number() {
                    return student_number;
                }

                public String getHead() {
                    return head;
                }

                public String getMood_words() {
                    return mood_words;
                }

                public String getNick_name() {
                    return nick_name;
                }
            }
        }
    }
}
