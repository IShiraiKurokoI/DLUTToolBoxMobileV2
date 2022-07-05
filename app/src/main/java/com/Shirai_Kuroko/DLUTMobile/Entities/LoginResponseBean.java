package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LoginResponseBean {

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
        @JsonProperty("my_info")
        private MyInfoDTO my_info;
        @JsonProperty("verify")
        private String verify;
        @JsonProperty("easep")
        private String easep;
        @JsonProperty("score")
        private ScoreDTO score;
        @JsonProperty("rsa_key")
        private String rsa_key;
        @JsonProperty("tgtinfo")
        private List<TgtinfoDTO> tgtinfo;
        @JsonProperty("pword")
        private String pword;
        @JsonProperty("USER_FIRST_LOGIN")
        private String USER_FIRST_LOGIN;
        @JsonProperty("cas_IS_EXPIRED_PWD")
        private String cas_IS_EXPIRED_PWD;
        @JsonProperty("expires_skey")
        private String expires_skey;

        public MyInfoDTO getMy_info() {
            return my_info;
        }

        public String getVerify() {
            return verify;
        }

        public List<TgtinfoDTO> getTgtinfo() {
            return tgtinfo;
        }

        public ScoreDTO getScore() {
            return score;
        }

        @NoArgsConstructor
        @Data
        public static class MyInfoDTO {
            @JsonProperty("authority_identity")
            private String authority_identity;
            @JsonProperty("celphone")
            private String celphone;
            @JsonProperty("email")
            private String email;
            @JsonProperty("head")
            private String head;
            @JsonProperty("id_type")
            private String idType;
            @JsonProperty("identity")
            private String identity;
            @JsonProperty("identity_photo")
            private String identity_photo;
            @JsonProperty("isLogin")
            private String isLogin;
            @JsonProperty("isVisitors")
            private String isVisitors;
            @JsonProperty("landline")
            private String landline;
            @JsonProperty("mood_words")
            private String mood_words;
            @JsonProperty("name")
            private String name;
            @JsonProperty("nick_name")
            private String nick_name;
            @JsonProperty("org")
            private List<OrgDTO> org;
            @JsonProperty("org_id")
            private List<String> org_id;
            @JsonProperty("parent_active")
            private String parent_active;
            @JsonProperty("parent_activetime")
            private String parent_activetime;
            @JsonProperty("parent_bind")
            private String parent_bind;
            @JsonProperty("parent_passwd")
            private String parent_passwd;
            @JsonProperty("path")
            private String path;
            @JsonProperty("pycc")
            private String pycc;
            @JsonProperty("qqcode")
            private String qqcode;
            @JsonProperty("sex")
            private String sex;
            @JsonProperty("status")
            private String status;
            @JsonProperty("student_number")
            private String student_number;
            @JsonProperty("tittle")
            private String tittle;
            @JsonProperty("user_id")
            private String user_id;
            @JsonProperty("verify_phone")
            private String verify_phone;
            @JsonProperty("wechatcode")
            private String wechatcode;
            @JsonProperty("zzmm")
            private String zzmm;
            @JsonProperty("jid")
            private String jid;
            @JsonProperty("skey")
            private String skey;
            @JsonProperty("expires_skey")
            private String expires_skey;
            @JsonProperty("l_subject")
            private Object l_subject;
            @JsonProperty("l_title")
            private Object l_title;
            @JsonProperty("orgPath")
            private List<OrgPathDTO> orgPath;

            public String getName() {
                return name;
            }

            public String getNick_name() {
                return nick_name;
            }

            public String getStudentNumber() {
                return student_number;
            }

            public List<OrgDTO> getOrg() {
                return org;
            }

            public String getIdentity() {
                return identity;
            }

            public String getAuthority_identity() {
                return authority_identity;
            }

            public String getCelphone() {
                return celphone;
            }

            public String getEmail() {
                return email;
            }

            public String getHead() {
                return head;
            }

            public String getIdentity_photo() {
                return identity_photo;
            }

            public String getParent_bind() {
                return parent_bind;
            }

            public String getSex() {
                return sex;
            }

            public String getSkey() {
                return skey;
            }

            public String getQqcode() {
                return qqcode;
            }

            public String getJid() {
                return jid;
            }

            public String getWechatcode() {
                return wechatcode;
            }

            public String getTittle() {
                return tittle;
            }

            @NoArgsConstructor
            @Data
            public static class OrgDTO {
                @JsonProperty("end_time")
                private String end_time;
                @JsonProperty("flag_class")
                private String flag_class;
                @JsonProperty("id")
                private String id;
                @JsonProperty("last_time")
                private String last_time;
                @JsonProperty("name")
                private String name;
                @JsonProperty("organization_id")
                private String organization_id;
                @JsonProperty("parent")
                private String parent;
                @JsonProperty("path")
                private String path;
                @JsonProperty("role")
                private String role;
                @JsonProperty("sort_string")
                private String sort_string;
                @JsonProperty("start_time")
                private String start_time;
                @JsonProperty("status")
                private String status;
                @JsonProperty("type")
                private String type;

                public String getName() {
                    return name;
                }
            }

            @NoArgsConstructor
            @Data
            public static class OrgPathDTO {
                @JsonProperty("name")
                private String name;
                @JsonProperty("organization_id")
                private String organizationId;

                public String getName() {
                    return name;
                }
            }
        }

        @NoArgsConstructor
        @Data
        public static class ScoreDTO {
            @JsonProperty("id")
            private Object id;
            @JsonProperty("sum")
            private Integer sum;
            @JsonProperty("add_score")
            private Integer add_score;

            public Integer getAdd_score() {
                return add_score;
            }

            public Integer getSum() {
                return sum;
            }

            public Object getId() {
                return id;
            }
        }

        @NoArgsConstructor
        @Data
        public static class TgtinfoDTO {
            @JsonProperty("name")
            private String name;
            @JsonProperty("value")
            private String value;
            @JsonProperty("domain")
            private String domain;
            @JsonProperty("path")
            private String path;
            @JsonProperty("expires")
            private Integer expires;
            @JsonProperty("hours")
            private Integer hours;

            public String getValue() {
                return value;
            }

            public String getName() {
                return name;
            }
        }
    }
}
