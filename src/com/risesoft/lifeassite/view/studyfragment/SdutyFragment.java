package com.risesoft.lifeassite.view.studyfragment;

import com.risesoft.lifeassite.R;
import com.risesoft.lifeassite.view.studyfragment.dictionary.DictionaryFragment;
import com.risesoft.lifeassite.view.studyfragment.note.NoteFragment;
import com.risesoft.lifeassite.view.studyfragment.translation.TranslationFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;


public class SdutyFragment extends Fragment implements OnClickListener{

	View studyView;
	RadioButton noteRb,translationRb,dictionaryRb;
	Fragment childFragment;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		studyView=LayoutInflater.from(getActivity()).inflate(R.layout.study_layout, null);
		initView();
		initListener();
		return studyView;
	}
	private void initView() {
		// TODO Auto-generated method stub
		getChildFragmentManager().beginTransaction().replace(R.id.study_fragment, new NoteFragment()).commit();
		noteRb=(RadioButton) studyView.findViewById(R.id.study_tab_note);
		translationRb=(RadioButton) studyView.findViewById(R.id.study_tab_translation);
		dictionaryRb=(RadioButton) studyView.findViewById(R.id.study_tab_dic);
	}
	private void initListener() {
		// TODO Auto-generated method stub
		noteRb.setOnClickListener(this);
		translationRb.setOnClickListener(this);
		dictionaryRb.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.study_tab_note:
			getChildFragmentManager().beginTransaction().replace(R.id.study_fragment, new NoteFragment()).commit();
			break;
		case R.id.study_tab_translation:
			getChildFragmentManager().beginTransaction().replace(R.id.study_fragment, new TranslationFragment()).commit();
			break;
		case R.id.study_tab_dic:
			getChildFragmentManager().beginTransaction().replace(R.id.study_fragment, new DictionaryFragment()).commit();
			break;

		default:
			break;
		}
	}
	
	
	
}
