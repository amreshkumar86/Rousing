package com.niveus.oen.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.niveus.oen.R;
import com.niveus.oen.Utils.FontsProvider;

/**
 * Created by Adarsh on 01-Jun-17.
 */

public class PowerFragment extends Fragment {

    FontsProvider fontsProvider;
    TextView powerUsageTv;

    public PowerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_power, container, false);

        fontsProvider = new FontsProvider(getActivity());

        powerUsageTv = (TextView) view.findViewById(R.id.power_usage_tv);
        powerUsageTv.setTypeface(fontsProvider.getBold());

        final GraphView graph = (GraphView) view.findViewById(R.id.graph);
        final LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 4),
                new DataPoint(2, 1.5),
                new DataPoint(3, 2.5),
                new DataPoint(4, 6)
        });

        series.setColor(Color.parseColor("#C2C2C2"));
        series.setBackgroundColor(Color.parseColor("#C2C2C2"));
        series.setDrawBackground(true);

        graph.getGridLabelRenderer().setHumanRounding(true);
        //graph.getGridLabelRenderer().setNumHorizontalLabels(7);
        graph.getViewport().setScrollable(true);
        graph.addSeries(series);

        return view;
    }

}
