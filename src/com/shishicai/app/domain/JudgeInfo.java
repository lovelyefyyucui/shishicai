package com.shishicai.app.domain;
/*
 * 开关 
 */
public class JudgeInfo
{
    private String id;
    private String mcih;
    private String name;
    private String open;
    private String links;
    public void setId(String id) {
         this.id = id;
     }
     public String getId() {
         return id;
     }

    public void setMcih(String mcih) {
         this.mcih = mcih;
     }
     public String getMcih() {
         return mcih;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setOpen(String open) {
         this.open = open;
     }
     public String getOpen() {
         return open;
     }

    public void setLinks(String links) {
         this.links = links;
     }
     public String getLinks() {
         return links;
     }


    @Override
    public String toString() {
        return "JudgeInfo [id=" + id + ", open=" + open + ", link=" + links + "]";
    }
}
