package com.risesoft.lifeassite.view.disportfragment;

import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.view.disportfragment.joke.JokeFragment;
import com.risesoft.lifeassite.view.disportfragment.movie.MovieFragment;
import com.risesoft.lifeassite.view.disportfragment.star.StarFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;

public class DisportFragment extends Fragment implements OnClickListener{

	View disportView;
	RadioButton movieRb,cartoonRb,jokeRb;
	Fragment disportChildFragment;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		disportView=LayoutInflater.from(getActivity()).inflate(R.layout.disport_layout, null);
		initView();
		initListener();
		return disportView;
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		getChildFragmentManager().beginTransaction().add(R.id.disport_fragment, new MovieFragment()).commit();
		movieRb=(RadioButton)disportView.findViewById(R.id.disport_tab_music);
		cartoonRb=(RadioButton)disportView.findViewById(R.id.disport_tab_cartoon);
		jokeRb=(RadioButton)disportView.findViewById(R.id.disport_tab_joke);
	
	}
	private void initListener() {
		// TODO Auto-generated method stub
		movieRb.setOnClickListener(this);
		jokeRb.setOnClickListener(this);
		cartoonRb.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.disport_tab_joke:
			getChildFragmentManager().beginTransaction().replace(R.id.disport_fragment, new JokeFragment()).commit();
			break;
		case R.id.disport_tab_music:
			getChildFragmentManager().beginTransaction().replace(R.id.disport_fragment, new MovieFragment()).commit();
			break;
		case R.id.disport_tab_cartoon:
			getChildFragmentManager().beginTransaction().replace(R.id.disport_fragment, new StarFragment()).commit();
			break;
		default:
			break;
		}
	}
	
}
