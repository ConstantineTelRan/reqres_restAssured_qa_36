package model.response;

import java.util.ArrayList;

public class ListUsersRespDto {
    public int page;
    public int per_page;
    public int total;
    public int total_pages;
    public ArrayList<DataDto> data;
    public Support support;
}
