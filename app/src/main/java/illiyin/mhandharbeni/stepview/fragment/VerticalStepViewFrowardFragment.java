package illiyin.mhandharbeni.stepview.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import illiyin.mhandharbeni.stepview.R;
import illiyin.mhandharbeni.stepviewlibrary.vertical.VerticalStepView;

/**
 * Created by root on 2/8/18.
 */

public class VerticalStepViewFrowardFragment extends Fragment
{
    View mView;
    private VerticalStepView mSetpview0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mView = mView.inflate(getActivity(), R.layout.fragment_vertical_stepview, null);
        return mView;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        mSetpview0 = (VerticalStepView) mView.findViewById(R.id.step_view0);

        List<String> list0 = new ArrayList<>();
        list0.add("A，B");
        list0.add("C");
        list0.add("D");
        list0.add("E");
        list0.add("F");
        list0.add("G");
        list0.add("H");
        list0.add("I");
        list0.add("J【K】L，M【N】");
        list0.add("O【P】Q");
        list0.add("R【S】T，U【V】");
        list0.add("W【X】Y，Z");
        list0.add("A【B】C，D【130-0000-0000】，E，F");
        list0.add("G，H！");
        mSetpview0.setStepsViewIndicatorComplectingPosition(list0.size() - 2)//设置完成的步数
                .reverseDraw(false)//default is true
                .setTextSize(14)
                .setStepViewTexts(list0)//总步骤
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getActivity(), android.R.color.white))//设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getActivity(), R.color.uncompleted_text_color))//设置StepsViewIndicator未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(getActivity(), android.R.color.white))//设置StepsView text完成线的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(getActivity(), R.color.uncompleted_text_color))//设置StepsView text未完成线的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getActivity(), R.drawable.complted))//设置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getActivity(), R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getActivity(), R.drawable.attention));//设置StepsViewIndicator AttentionIcon

    }
}