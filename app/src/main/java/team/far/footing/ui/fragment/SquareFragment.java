package team.far.footing.ui.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;

public class SquareFragment extends Fragment {

    @InjectView(R.id.tv_square_title) TextView tvSquareTitle;
    @InjectView(R.id.tv_square_sub_title) TextView tvSquareSubTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_square, container, false);
        ButterKnife.inject(this, view);
        initFonts();
        return view;
    }

    private void initFonts() {
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/square_fonts.TTF");
        tvSquareTitle.setTypeface(face);
        tvSquareSubTitle.setTypeface(face);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
