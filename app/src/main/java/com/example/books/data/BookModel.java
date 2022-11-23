package com.example.books.data;

import com.example.books.R;

import java.io.Serializable;

/**
 * BookModel
 */
public class BookModel implements Serializable {

    // id
    private String id;

    // title
    private String title;

    // author
    private String author;

    // publisher
    private String publisher;

    // intro
    private String intro;

    // like
    private boolean like;

    // bookmark
    private boolean bookmark;

    private int likeCount = 0;

    private int bookmarkCount = 0;

    public BookModel(String id, String title, String author, String publisher, String intro) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.intro = intro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getBookmarkCount() {
        return bookmarkCount;
    }

    public void setBookmarkCount(int bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
    }

    public int getItemImageId() {
        switch (id) {
            case "101": return R.mipmap.b101t;
            case "102": return R.mipmap.b102t;
            case "103": return R.mipmap.b103t;
            case "104": return R.mipmap.b104t;
            case "105": return R.mipmap.b105t;
            case "106": return R.mipmap.b106t;
            case "107": return R.mipmap.b107t;
            case "108": return R.mipmap.b108t;
            case "109": return R.mipmap.b109t;
            case "110": return R.mipmap.b110t;

            case "201": return R.mipmap.b201t;
            case "202": return R.mipmap.b202t;
            case "203": return R.mipmap.b203t;
            case "204": return R.mipmap.b204t;
            case "205": return R.mipmap.b205t;
            case "206": return R.mipmap.b206t;
            case "207": return R.mipmap.b207t;
            case "208": return R.mipmap.b208t;
            case "209": return R.mipmap.b209t;
            case "210": return R.mipmap.b210t;

            case "301": return R.mipmap.b301t;
            case "302": return R.mipmap.b302t;
            case "303": return R.mipmap.b303t;
            case "304": return R.mipmap.b304t;
            case "305": return R.mipmap.b305t;
            case "306": return R.mipmap.b306t;
            case "307": return R.mipmap.b307t;
            case "308": return R.mipmap.b308t;
            case "309": return R.mipmap.b309t;
            case "310": return R.mipmap.b310t;

            case "401": return R.mipmap.b401t;
            case "402": return R.mipmap.b402t;
            case "403": return R.mipmap.b403t;
            case "404": return R.mipmap.b404t;
            case "405": return R.mipmap.b405t;
            case "406": return R.mipmap.b406t;
            case "407": return R.mipmap.b407t;
            case "408": return R.mipmap.b408t;
            case "409": return R.mipmap.b409t;
            case "410": return R.mipmap.b410t;

            case "501": return R.mipmap.b501t;
            case "502": return R.mipmap.b502t;
            case "503": return R.mipmap.b503t;
            case "504": return R.mipmap.b504t;
            case "505": return R.mipmap.b505t;
            case "506": return R.mipmap.b506t;
            case "507": return R.mipmap.b507t;
            case "508": return R.mipmap.b508t;
            case "509": return R.mipmap.b509t;
            case "510": return R.mipmap.b510t;

            case "601": return R.mipmap.b601t;
            case "602": return R.mipmap.b602t;
            case "603": return R.mipmap.b603t;
            case "604": return R.mipmap.b604t;
            case "605": return R.mipmap.b605t;
            case "606": return R.mipmap.b606t;
            case "607": return R.mipmap.b607t;
            case "608": return R.mipmap.b608t;
            case "609": return R.mipmap.b609t;
            case "610": return R.mipmap.b610t;

            case "701": return R.mipmap.b701t;
            case "702": return R.mipmap.b702t;
            case "703": return R.mipmap.b703t;
            case "704": return R.mipmap.b704t;
            case "705": return R.mipmap.b705t;
            case "706": return R.mipmap.b706t;
            case "707": return R.mipmap.b707t;
            case "708": return R.mipmap.b708t;
            case "709": return R.mipmap.b709t;
            case "710": return R.mipmap.b710t;

        }
        return R.mipmap.b101t;
    }

    public int getDetailImageId() {
        switch (id) {
            case "101": return R.mipmap.b101;
            case "102": return R.mipmap.b102;
            case "103": return R.mipmap.b103;
            case "104": return R.mipmap.b104;
            case "105": return R.mipmap.b105;
            case "106": return R.mipmap.b106;
            case "107": return R.mipmap.b107;
            case "108": return R.mipmap.b108;
            case "109": return R.mipmap.b109;
            case "110": return R.mipmap.b110;

            case "201": return R.mipmap.b201;
            case "202": return R.mipmap.b202;
            case "203": return R.mipmap.b203;
            case "204": return R.mipmap.b204;
            case "205": return R.mipmap.b205;
            case "206": return R.mipmap.b206;
            case "207": return R.mipmap.b207;
            case "208": return R.mipmap.b208;
            case "209": return R.mipmap.b209;
            case "210": return R.mipmap.b210;

            case "301": return R.mipmap.b301;
            case "302": return R.mipmap.b302;
            case "303": return R.mipmap.b303;
            case "304": return R.mipmap.b304;
            case "305": return R.mipmap.b305;
            case "306": return R.mipmap.b306;
            case "307": return R.mipmap.b307;
            case "308": return R.mipmap.b308;
            case "309": return R.mipmap.b309;
            case "310": return R.mipmap.b310;

            case "401": return R.mipmap.b401;
            case "402": return R.mipmap.b402;
            case "403": return R.mipmap.b403;
            case "404": return R.mipmap.b404;
            case "405": return R.mipmap.b405;
            case "406": return R.mipmap.b406;
            case "407": return R.mipmap.b407;
            case "408": return R.mipmap.b408;
            case "409": return R.mipmap.b409;
            case "410": return R.mipmap.b410;

            case "501": return R.mipmap.b501;
            case "502": return R.mipmap.b502;
            case "503": return R.mipmap.b503;
            case "504": return R.mipmap.b504;
            case "505": return R.mipmap.b505;
            case "506": return R.mipmap.b506;
            case "507": return R.mipmap.b507;
            case "508": return R.mipmap.b508;
            case "509": return R.mipmap.b509;
            case "510": return R.mipmap.b510;

            case "601": return R.mipmap.b601;
            case "602": return R.mipmap.b602;
            case "603": return R.mipmap.b603;
            case "604": return R.mipmap.b604;
            case "605": return R.mipmap.b605;
            case "606": return R.mipmap.b606;
            case "607": return R.mipmap.b607;
            case "608": return R.mipmap.b608;
            case "609": return R.mipmap.b609;
            case "610": return R.mipmap.b610;

            case "701": return R.mipmap.b701;
            case "702": return R.mipmap.b702;
            case "703": return R.mipmap.b703;
            case "704": return R.mipmap.b704;
            case "705": return R.mipmap.b705;
            case "706": return R.mipmap.b706;
            case "707": return R.mipmap.b707;
            case "708": return R.mipmap.b708;
            case "709": return R.mipmap.b709;
            case "710": return R.mipmap.b710;

        }
        return R.mipmap.b101;
    }

}
