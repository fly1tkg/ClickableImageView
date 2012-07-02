
package jp.ddid.takagi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class ClickableImageView extends ImageView {

    /** クリック中にオーバーする色 */
    private static final int DEFAULT_CLICKED_COLOR = 0xAA000000;

    /** Imageがクリックされているかどうか */
    private boolean mClicked = false;
    /** オーバーレイする色などを保持します */
    private Paint mPaint;
    /** オーバーレイする色の形を保持します */
    private RectF mRectF;

    /**
     * クリックされたときに#AA000000の色をオーバーするImageViewを生成します
     * @param context
     */
    public ClickableImageView(Context context) {
        super(context);
        setup(DEFAULT_CLICKED_COLOR);
    }
    
    /**
     * クリックされたときに指定の色をオーバーするImageViewを生成します
     * @param context
     * @param clickedColor クリック時の色
     */
    public ClickableImageView(Context context, int clickedColor){
        super(context);
        setup(clickedColor);
    }

    /**
     * クリックされたときに指定の色をオーバーするImageViewを生成します
     * 指定が無い場合は#AA000000の色をオーバーします
     * @param context
     * @param attrs
     */
    public ClickableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        int clickedColor = getClickedColor(context, attrs);
        setup(clickedColor);
    }

    /**
     * クリックされたときに指定の色をオーバーするImageViewを生成します
     * 指定が無い場合は#AA000000の色をオーバーします
     * @param context
     * @param attrs
     * @param defStyle
     */
    public ClickableImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        int clickedColor = getClickedColor(context, attrs);
        setup(clickedColor);
    }

    /**
     * タッチが始まったときはmClickedをtrueに
     * タッチが終わったときはmClickedをfalseにします
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // タッチが始まったときはmClickedをtrueにします
                mClicked = true;
                break;
            case MotionEvent.ACTION_UP: // タッチが終わったときはmClickedをfalseにします
            case MotionEvent.ACTION_MOVE: // タッチではなくフリックやスクロールの時はfalseにします
                mClicked = false;
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    /**
     * クリック中の時のみ(mClicked == true)指定色をオーバーします
     */
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == mRectF) {
            // 指定色の四角形を作ります
            mRectF = new RectF(0.0f, 0.0f, getWidth(), getHeight());
        }
        if (mClicked) {
            // ImageViewがクリックされている間、指定色をオーバーレイします
            canvas.drawRect(mRectF, mPaint);
        }
    }

    /**
     * xmlからclickedColorの色を取得します
     * @param context
     * @param attrs
     * @return
     */
    private int getClickedColor(Context context, AttributeSet attrs) {
        // styleable から TypedArray の取得
        TypedArray tArray =
                context.obtainStyledAttributes(
                        attrs,
                        R.styleable.ClickableImageView
                        );
        // TypedArray から Color を取得
        return tArray.getColor(R.styleable.ClickableImageView_clickedColor, DEFAULT_CLICKED_COLOR);
    }

    /**
     * オーバーレイする色のPaintを生成します
     * メンバ変数に保存して、再利用できるようにします
     */
    private void setup(int color) {
        // 指定色をセットします
        mPaint = new Paint();
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);
    }

}
