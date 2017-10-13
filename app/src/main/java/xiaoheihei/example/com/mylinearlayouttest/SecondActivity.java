package xiaoheihei.example.com.mylinearlayouttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class SecondActivity extends AppCompatActivity {
    private static int IMG_COUNT = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        final WaterfallLayout waterfallLayout = ((WaterfallLayout) findViewById(R.id.waterfalllayout));
        findViewById(R.id.buttonid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView(waterfallLayout);
            }
        });

    }

    public void addView(WaterfallLayout waterfallLayout) {
        Random random = new Random();
        Integer num = Math.abs(random.nextInt());
        WaterfallLayout.LayoutParams layoutParams = new WaterfallLayout.LayoutParams(WaterfallLayout.LayoutParams.WRAP_CONTENT,
                WaterfallLayout.LayoutParams.WRAP_CONTENT);
        ImageView imageView = new ImageView(this);
        if (num % IMG_COUNT == 0) {
            imageView.setImageResource(R.drawable.d);
        } else if (num % IMG_COUNT == 1) {
            imageView.setImageResource(R.drawable.f);
        } else if (num % IMG_COUNT == 2) {
            imageView.setImageResource(R.drawable.g);
        } else if (num % IMG_COUNT == 3) {
            imageView.setImageResource(R.drawable.a);
        } else if (num % IMG_COUNT == 4) {
            imageView.setImageResource(R.drawable.b);
        }
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        waterfallLayout.addView(imageView, layoutParams);

        waterfallLayout.setOnItemClickItemListener(new WaterfallLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int index) {
                Toast.makeText(SecondActivity.this, "item=" + index, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
