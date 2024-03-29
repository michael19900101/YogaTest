package com.aotuman.yogatest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.view.ViewGroup;
import android.util.Log;

public class BenchmarkInflate extends BenchmarkFragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Button b = (Button) rootLayout.findViewById(R.id.btn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBenchmark();
            }
        });

        return rootLayout;
    }

    protected void startBenchmark() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        TextView textView = (TextView) rootLayout.findViewById(R.id.text);

        final int ITERATIONS = 500;

        inflater.inflate(yogaLayout, null);
        inflater.inflate(linearLayout, null);

        BenchmarkAggregator yogaInflationAggregator = new BenchmarkAggregator("Yoga Inflate");
        BenchmarkAggregator linearInflationAggregator = new BenchmarkAggregator("Linear Inflate");
        for (int i = 0; i < ITERATIONS; i++) {
            yogaInflationAggregator.startTrace();
            inflater.inflate(yogaLayout, null);
            yogaInflationAggregator.endTrace();
            linearInflationAggregator.startTrace();
            inflater.inflate(linearLayout, null);
            linearInflationAggregator.endTrace();
        }

        textView.setText(
                yogaInflationAggregator.toString()+
                        "\n"+
                        linearInflationAggregator.toString());
        Log.i(
                "YogaLayoutBenchmark",
                yogaInflationAggregator.toString()+
                        "\n"+
                        linearInflationAggregator.toString());
        rootLayout.invalidate();
    }
}
