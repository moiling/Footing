package team.far.footing.model.callback;

import org.w3c.dom.Comment;

import java.util.List;

/**
 * Created by luoyy on 2015/8/12 0012.
 */
public interface OnQueryCommentListener {
    void onSuccess(List<Comment> list);

    void onError(int i, String s);
}
