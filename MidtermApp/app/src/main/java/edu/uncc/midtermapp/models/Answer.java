package edu.uncc.midtermapp.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Answer implements Serializable {
    public String answer_id, answer_text;

    public Answer(String answer_id, String answer_text) {
        this.answer_id = answer_id;
        this.answer_text = answer_text;
    }

    public Answer() {
    }

    public Answer(JSONObject json) throws JSONException {

        /*
         {
                    "answer_id": "50",
                    "answer_text": "Olympic Swimmer"
                },
         */

        this.answer_id =json.getString("answer_id");
        this.answer_text=json.getString("answer_text");

    }

    public String getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(String answer_id) {
        this.answer_id = answer_id;
    }

    public String getAnswer_text() {
        return answer_text;
    }

    public void setAnswer_text(String answer_text) {
        this.answer_text = answer_text;
    }

    @Override
    public String toString() {
        return answer_text;
    }
}
