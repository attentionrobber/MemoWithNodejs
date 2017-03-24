package com.hyunseok.android.memowithnodejs.domain;

import java.util.List;

/**
 * Created by Administrator on 2017-03-24.
 */

public class Data {
    List<Qna> data;

    public List<Qna> getData() {
        return data;
    }

    public void setData(List<Qna> data) {
        this.data = data;
    }

    public class Qna {
        String _id;
        String title;
        String content;
        String name;

        public String get_id() {
            return _id;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public String getName() {
            return name;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
