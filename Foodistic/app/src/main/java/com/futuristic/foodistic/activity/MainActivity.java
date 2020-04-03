package com.futuristic.foodistic.activity;

import android.content.Intent;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.futuristic.foodistic.FoodCartModel.SliderItem;
import com.futuristic.foodistic.R;
import com.futuristic.foodistic.FoodCartAdapters.HorizontalAdapter;
import com.futuristic.foodistic.FoodCartAdapters.VerticalAdapter;
import com.futuristic.foodistic.FoodCartModel.Food;
import com.futuristic.foodistic.model.GeneralFood;
import com.futuristic.foodistic.rest.RetrofitClient;
import com.futuristic.foodistic.rest.RetrofitInterface;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    SliderView sliderView;
    private SliderAdapterExample adapter;
    RecyclerView recyclerViewHorizontal;
    RecyclerView recyclerViewVertical;
    public static TextView tv;
    public static List<GeneralFood> cartFoods = new ArrayList<>();
    public Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//Slider View...
        sliderView = findViewById(R.id.imageSlider);

        adapter = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(adapter);


//Toolbar..


        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Punjabi Virsa");
        toolbar.setTitleTextColor(getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        cartUpdate();
        renewItems();


        recyclerViewHorizontal = findViewById(R.id.horizontal_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHorizontal.setLayoutManager(linearLayoutManager);

        recyclerViewVertical = findViewById(R.id.vertical_recyclerview);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewVertical.setLayoutManager(linearLayoutManager2);

        RetrofitInterface retrofitService = RetrofitClient.getClient().create(RetrofitInterface.class);

        Call<Food> call = retrofitService.getFoods();
        call.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                List<GeneralFood> popularFoods = response.body().getPopularFood();
                recyclerViewHorizontal.setAdapter(new HorizontalAdapter(popularFoods, R.layout.recyclerview_horizontal, MainActivity.this));

                List<GeneralFood> regularFoods = response.body().getRegularFood();
                recyclerViewVertical.setNestedScrollingEnabled(false);
                recyclerViewVertical.setAdapter(new VerticalAdapter(regularFoods, R.layout.recyclerview_vertical, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {

            }
        });
    }

    public void renewItems() {
        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(1);
        sliderView.setAutoCycle(false);
        sliderView.setIndicatorVisibility(true);
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 5; i++) {
            SliderItem sliderItem = new SliderItem();
            switch(i){
                case 0:
                    sliderItem.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSEEJ26-Nf4VDiwtJUQOc720UV_4UxfD1xeBJ_R1m-UBFQkgeiE");
                break;
                case 1:
                    sliderItem.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcS7M8M5Nf-AxOywEodbPxiS7QQbOze88RhbS0zF0lywIQp_CZZ_");
                    break;
                case 2:
                    sliderItem.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcS8GW15oxfJ0LvV5mnsJgzIJHqidMaEXL1TDkzl0h04lRDLzXHb");
                    break;
                case 3:
                    sliderItem.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTyURLEnUe7f-fWh2IFCV0Vc7S5mVedv7VgquqYVR5JmA7Xhbhv");
                    break;
                case 4:
                    sliderItem.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcS8GW15oxfJ0LvV5mnsJgzIJHqidMaEXL1TDkzl0h04lRDLzXHb");
                    break;
                default:
                    sliderItem.setImageUrl("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMSEhUSEhMWFRUXFRgVFxUWFxYXGBYYFxUXFhYWFRYYHSggGBolHRgYITEhJSkrLi8uFx8zODMtNygtLisBCgoKDg0OGxAQGzAmICUyLy0wLSstLS0tLS0tLy0tLS8tLS0vLS0tMC0tLS0tLS4tLS0vLS0tLS0tLS0tLS0tLf/AABEIAK8BIQMBIgACEQEDEQH/xAAcAAABBQEBAQAAAAAAAAAAAAAFAQIDBAYHAAj/xAA/EAACAQIEAwYDBAkEAgMBAAABAhEAAwQSITEFQVEGEyJhcYEykaFCsdHwBxQWI1JiweHxFTNDU3KSY4LCF//EABoBAAMBAQEBAAAAAAAAAAAAAAIDBAEFAAb/xAAwEQACAgEDAgQFBAIDAQAAAAABAgARAxIhMQRBEyJRYRRxgZHwBTKh8bHRUsHhI//aAAwDAQACEQMRAD8A45Fey0sU4Ck3KI0LS5afSxWXNkcUuWngUsV656RxSgVJlpQtZc9UjilAqXJShKy5tSICnRUoSlyVmqeqRAUsVMEpclZqm6ZDFKFqYW6eLdYWm6ZAFp4Sphbpwt0JaEFkQt0vd1ds4R20VWPoDU44VezZe7aYmI5Us5VHJjBiY9oMFuni3VtsOVMMCD0NKLdbrntErKlSKtWRbqWxhixCqJJ5Cs1zdEqBaeqV0Ds/+jx7vivHKOg3rW4TsfhrP2BPU60QVjALqNpxq1g3bZGPsatpwa8f+Jq7P+qWl2UfKkCp0oSJmsek45/od/8A6z9Kjbg90b22rsxRelMOHU/ZrLm6pxdsE43Vh7Go+6rtDYBDutU8R2etP9kV6zCsTkndUvdV0LF9jEPwyPSgeM7L3U28Q+Rr2qaKMzBtV7uqI3MOVMMCD50w2q3VN0Sh3deq93VerdUzTMRkpctTRShafqk+mQZaUJU+SlCVmqbpkGSlC1OLdOFus1TdMrhaeFqfu6UW6wtN0SDLTgKnFuni1WF4QSQAUoFWBapws0OubolfLTwlTi1Ui2aEvCCSBbVSC1VgWqetqhLQ9EqC1RbgXhc6AyNQfnVcWaucLSLi0jMbQx2EU4MK3uI20LSIB8Q1jl9In0MU3CcQN5iymVAmOfnJ51S4zZZsuTKZYAjll5+9VMFhrtlyBBQyRHIVEMaMl950GytxU2HGDZvMFK+F18JgqbbhNQWOjaxoOR84rHNhoJBGoMH2rS8NulwqmCASyzurEAZp9NI29xVLi2HZbrBxB0PzA1EUzFlOrTJsmMaR6wQLFdG/R7wBQvesJJ28qxCJXUOxeKBtBelW4SC4uRdQCE2mjJy7VUvKTRBVk168BVzCxOaDAd2y3Sq5DdKNs4pBlNSlQTzHhoDN0jka8uJFHGsrVW9YtxyoTjYd4YYHtKa3xUocVCcGrfCflUFzCXF21pQdoW0uxTWXrVBcWVMMIq3axINe8QTdBlTG8JtXRDKKyHGezb2ZZfEv1Fb4iaW0QfC3OmqQ209qKzkvdUldV/Z6z/APlXqZ4TTfiEnzoFpwSp0t09bRoi0ELKwSnqlWhhz0qQYagLwgkqKlPC1Z/VqQ4U0OuHokKrUgt1Jbw5FTrZPShLQgsrrbqVbVW7GCdtkY+gJolY7PYhtrTe4j76EtCoQKLNPWxWltdj8UfsR6mrlvsPif5R7n8KzzT1oO8yAsU8WK2adhL/Nl+tTL2Dvfxr8jXtL+k3xMfrMQtipBZrafsLeH2l+Rpp7E3+q/Ws0P6TfEx+syAtVLYtjMJMedahuyGIHJT7/2qG52ZxA/459CKAqxFVDXInrM9icOUD/xIQ/qp0NewWL1nn/ajmJwlxQuay5AGVzE+EyNI6b1ne4yPlB0mA06R1npFTnGaphLUYPxC/DL658pWc0gNMESI9CPw3r2MRWPhnQQZ6gmvcKty6tAhdARrJjn0OszRHGYYZ2IESZ9SdzU6msv0gvRBEDizRvs7i2tvE6VX7iltKVINVB5OyAidLwWPBAnSroM1j8BjAYHOjFrFFavx9RtTTlvg32l7G2pBisliOJPaaCZrUrjQdDVHiHB7V0Hr1r2XEuTcSnpHxoazDaDG44zWzG8VnLeIxBJBcxRr/RbtsEKcwoY+CvAxkNTmwd518DdOhIQjf1hXBYwWwoJ/wA1pUxOkjWsE+EugQyE9NK0XAsPcCjvJFEgANDmT9Z0+KtYIuGLmGW4NRQrF8Oe3quorSWVEU51BEU98KsPecjxaO3EyuFxvI0Tw65mEVG3ApuTMDpRW7ctYdNxt70vDgYG32Am5MgOy8yfLXqAftIvQ16rPiMfrE+Dk9Jwq2Knt0Rs8IPOrtnhYGkTUZMu2gq2pPKrVvDE8q1/Ceyty4ASuRep3+Vavh/ZexbgkZj1P4US42aA2VROa4Lgdy4fCjH20+Zo3hOwdxtXIX6mukW7SroABUkUwYPUxJzntMdhOwtlfiJajOF7PYdNra/IUYikiiGECCcrHvILeFRdlA9qmVB0FLFKBRgVFkzwp1JQftP2ht4G13lzxMTlRBu7f0HU0fuYtmCizDLsAJJAHU0J/aTDF8iXA7c8p0EdW2FYjtFiXu2DcxjkXGUNbwdltApEhroGp066abVY7K9n8LcK3LtxL93KpGHtkG3bEQC69epPypZynVQEnDZcu+MbepmoXtGXYrYstdIMEqRk9c+0emtHbZMCRB5isBxDtfes5rdtbQKsV+E5dGIga7fh8jvYvipvW3a4wLZs55fFyidAABFLTqlZwt8/QRv7W0k2ZpK9FDcbx7D2vjuqD0Bk/IV7hvHsPfMWrgJ6QR99Ua0urFx5xuBqo1CBtg8hXD8dcdMTfzIBFy6oESAhYwAfTQHlpXcxXIf0hMEusY1Nxj8uXvU3WjyqBLP05gHPylHhd0MykEgmQTuR4jPuND7VueE8M/WrQLEqyyJ666Hzrl/Zhznc6wTJ6KW1yx+eddk7I3lyEZtWIIBjpBiOUg1z8eMDqdJ4l3UufB1LzBd/svdX4WDeulDb/DbqfEh9RrXQcTiEtgtcZVAEksQBHvQi12owjsFFzcwCVYAnYakRVz9PiBq6kGPPmYWFsfKZfDaeRotZxkb1or2AtvuoobiuAKfgMUD9I43WCM6tzIBdU143CNjVW7w+6nKfSqL3yu/1qe3XkRgo8GFf1xxzmlHEzzWglziECqA41rFF45E94YM1v+rjmtOPHF6UCtY1SJpxvLTRnMA4xDZ4+OQqJuOsdhQZryimpdnUUXjGD4Y9JfxHGXmCYnpVa+5bczUaYfM0mrP6ux5VO7s0pxoq7yr3Qr1W/wBXbpXqHeMsQTwzhL3jCjQbsdhWx4ZwK3a1jM38R/p0olYsqgCqAAOQqSukmILzOc+QtEivUtLFNiokUoFLS16ZcSKSKdWZ7Wdn7+LgJfyJAlJYBt5By9ffahdio2FwSSBtLvGO0NjDrL3EkGCudM0dQsyfShj/AKQOHgT3xPkLdw//AJisS3Ytlv8Ad93KEAQz67gs2ZQNwCIjnVXjvArVshUKZgTmFvPAG4kuxGbYRpUPxg1V/EwJ1LilT+Zvl/SHgNZuOv8A5W319IBrn/Ee0y3uILibv721bI7tACogaxDc53POKx+Kv6wBykT5HX3qAYgiC6nLy0In0NUanYSTOmYnw3qbPivbA3Ll26iMLt0BCQQAqaDKuhJMc6odnO017BXLj2LSE3Fyw+Zog7+GJ1FNfD3cMwD2QGdFZULA+E8wQSZM/Q1ZXiip+7VVIVm2y6k6kgNqYMgSdoiKn1FNwLPznQyF8OHSX57ACHrQV0S83iVjLSIymR3ixGwI3/oZpeL8Pc2mQXAkgS5JQAq4O66wdVA8h1odhsXdAfPDWWGZu8BXLGggAiOWw1neYosMcrWGt3jo+UKY1BY/EfIenIVymDo4I33/AD7TnbltRmSw982nR87uw18UFSdtZExRCz2wxFsstpUAYjw5ASSOhmY9P70PbBkubYtXCwmQGSfbSGmNIOsaVXbhF6f9m7H8oVmmY2U9eW9dVV1HV3gHM1+Y/wAzecJ7eMxRb2VX2XKZ5DRxJ1mfzrQztTxNMQORIbMT8wfXQ7UBw/DWVXYeG4upVlCsBsSFY/0mZrRcPv4U2A7jKGgMzqv+4JBZW3310j6VNntW1Wa22959B+lZNaHUPkYK4bdXvVlZC8vhYj4gSRy5+/StZ2au90HuBiYI0bKSAeeZRt56fdWHtwrEqyuNdR6jpWn4XfZcoB1IEHQSC0b7cudJzEg2J2GAKkGE+0+GvYpFvl1yROQHUxOsaxWN4xj7xFkC33YIhFUAowGhfPpJ+GQdproGLvKUhMwyGShaCp1EtEMRJ3PWKyV/GvexDqwCC2AFHMBQMw853o1dNzztvFY2NAcD/E1XD+0luyQnfWyAg8DNlKjmdtT5bn2rVcJ4muITOugmPceVcd4hbs25v3A+XOVBUSDcyllHkDHnFbfhuMXuVu4Y6E/ASsyB4gQNCeuoO1WYcuRADuV/OJL1PTY34/d+czak1WxOAt3B4lFR8NxneKJENAJB00OxHlV5a6AK5FvtOQQyNR5mV4l2UJk2m9jWTxvDblk/vEI8+XzrrAFNu2FcQwBHnSW6dTxGrnbvOWWL1W0uCtDxbscrS1g5D/D9k+3KsnibFyy2W6pXoeR9DUz49PIlCuG4hDMDpU+GXZRz0oVaxA61ewWJy3FPQg0u4embHComGUswBJHOshxrtIQTkBjyFaPFqb1xCNU0McpHI0naPCviGHhAAEbE+5MUbF2B0DYce8jL0d5gP2pf+b5UlaD9mG6L8j+FepNZv+JnvE950EClAr1KK68Xc9FepRS16p64gp9u0W2ph03qpj+KZU8JA8zpoNzS8uVMS6nmqjOaWWsTdRDBbWgfEuPKpyKSzfwqJPv0oEcZdv3czfu7IJAEeO4f4m6DypmPxSWnAEQ28RpJAlj1J5VzcnW5MmybD+Z0cXRqp825lftJjcyXCrAOqz4jqdRGk9awNzil03Et3SkKTJSMsMI8RBEjy3GtHOJ9jr+Lxjk4hEsgbgksoQbFIiQ86k0GweCsW1m4BiLukx/sqRMED7Z13PyFbjwqg1ZCCT/E3GHObyjbj+43GcKRQt7Pahd7UNnYAgsRuCYPw6afKtXxfEWsZhlw1m2WzESzCBaykayfLpy051lMQ6AZV5tsPh2jQCIEHlUOHt3CxW2zgRLZWIUefl6VjIMmlr3Efm6MFix7wp214gty8FRMpVEt558RRMxC/wAolieu1DsCW1LNlAGjE9Jn/O9DreMkyxkzEkSTvWhwGDz2gxAgtlAAIk8/WPxosnkXecsdBkyPbEQbicY9wOiqcigayRLEeEZdpmedFuz2LFwC0yHOixm3kgQZnbX8iqHGUgjD2ZLK3eXSglpOyiOgj6Vd7P4S80i3K3Fk+LcydVM/DI++lZNHhXx+cy0fp+MoV/mFMNwC5iUt3kVQQpBZ3gNDnQKFJMa6mN/Kh1zjF6yy2zlZy5QD4pMxJYGTv6a+tEsFeuCRcBAtkOAZABzZiI20In3qzxHs/PEFuaG1mzZQSGzG2WABGwLDedPesQ7bnYScdDiFBlBMznabimJQpauW0YSQJWIAC6A9IP1qseIKy2UIy5GmBqCToB57mj2Ma0MTOJaQjFAjANnVkhtY1YZjqvNBVY8Nv2LlxFtIberJiCoz5W2UknQrMbctqMuujzc/n/UtwY1xGlEH3LUMqKAIknkBrIGm5Mmi3C8Qi6MywejD5AHX/NAlsMrEZ2JBlm3OpJkxttVqyhECQVBmI5nnrSnUEbmXHcTY38Li8QA9lUOVIDm5lLKCQNAdWBHPce1BmuXbV+3euW81whlf4SDEKNBpIHnNavs1jbbIttpEN4WMEKBpqYjn5E6etU+O8PcKxclmAJBnUhYIfqQd4+tY22MFfrJUbznG/wBIFxXBrt7CrasjVLnekEwSYMKs6TqeY2rO8OuMLozTJaGneSYM+c10ns9iAVDPzAkjXkNvmPnVbiPZBbmKN4XAocq2WNGIABIPXTaKDDk14iDzc0sEyb7CULeLxGCfvipuWW6ahRMZgRtJ61vsBjVu21uJ8LCR+HrWfuYjuLTrdXwAXCZGh0MR1FXex1nLgrKnRsskHkSST99dDost7X8wfWR9aoZddb3yO4h5XqQGqStrFWENXzm1JarY7ApeUpcUMD1qwDS14i54GpzHi/YO7aud5Ycva5ofiX0PMV5BBUV02s92g4D3n72zAuDUryf8DU2XATusqxZq2aUMDjTb226UYw+OV/tFT0kR7TpWTw+IklSCGBhlO4PQipTcpCZXx7Rr4VebDP8AzN8rderH/rBr1P8Ai/aI+DHrN+K9SUoquTxRTlFMZoE0KxvHFRGZIzLuD0nUxzoXyKgLNDTGzmlj+LcRVJVjoRpM/IGsvjMUbrqmU5IlmgwVHKepJ+hqzc4+XLApmkbHxL921CcRcYg6ZRB20A8gOdfOdRmbK5bcjttU7ODB4QoijGcW4uigKoZueUcvlvQPimP7pwTDsVDIpXVSwMyNZYRv5nSovDbVmdi5j4joB1JHttXuG4kXHLFgcql3Y9NwPIEx8qdixhBsJWABxKdnG3Vzs0y65TB2XmPfb2qndViBlXKg0A2+fnVi27XEUKursxjrLGJ9qv8AH7Tqq2MOofulz3SNyzclHOBPzFNsloTkKJnLtoJJLbeX960WCwhSzliGdQ7e85F8tiY9aBcNw/ePN4Qq+Jienv8AnStbgMWt3O7DKgMAySWAERkG2nXrRZn0iolDe8B8N7MaF7gj+Ebk66GB0olisEyoqBiQq+FEEuWO5MaKSeZqvxfjTOC1mUCE+F0gMAeTERFD73ajEPCgqoGnhED79qFFzZNz9oLELxCvD8+Ew5LKxaQWtqwaCzBRqN5kSfWr3BcSzYhWykGCrKNcsjUtG4BgVlv9auw4GQLo7GDOkHcH7+la/CWjgVtG4C168hZ8o+BSQCGPKTAnqDWZulfSzN3gpnT9oMO8fud5YuW8wR7d1csmc4KqeWo0aP8A61FaxiIDMtlC25HxEuQGIjn+FZ3G8SsO6kuczJqbZUksGC5QNSRBGp1kac5zZ43cVi6XBlDy6BZdQojVeaxzB08pNLHRuwUrsAIAKAEEzqK8GsSzqzWrmkM5DqDlAByzt4ROo2oTjcJdLmxdIDd2zoSZW6VjVW03B1nURtUvBOMW8XhwVhuWvxIeaNGvTX76Df6orDxKc1skBVElWEgowHLQikkhjTLuPofqOPtGYsbdjt+cQXfVGthFSbpYEueY6QNgJ+k1QRnQJmAg7HeRMfMdPMUW4uqgm9bmLokAaZWjxaRpInTqT6VQwOPP6vdw9xQ0EhM3ha2fsmOo3jyM71ViGpL/AD8Eaxo0IZ4PjCGBQ67xybyPT+3KtglwX1GY5bgMAHQ7ayQvi9zsPOuXYHE5WB862vCscp+MTpodd9APTrNKYabU8GKypdMORLuCGR2XMAsh03jYBl185+dXeL96y2xaXOC4UgkgJm0z+Ez02NCb4NxTp4hqBO7AcvarHCOK5PC+h2IOhB8xyrmVT6h9YWTGXWG8Zgxetm07nwrAJ+00cyZIWnIhQfagQSBqRS8NxILdR1P9aJd1qW3I5jeOhFVY9XUDV71Q9Pf6VI2bw/IZR/W0UrDTPXQj1olh3mqOMw9ts1xgGyjXqIpnC8chlVO3Wung6hcRGM/STvhLAuB84bBpZqNGmnV1AbklRa9XqStgzOdrOAG8O+saX1G3K4B9k+fQ1i+H8YW54W8LDQg6EEaEEda6vNcv/SjwI2ri42yIVyFugcn5P6NsfMDrScuENuI7FlK7GWc460lYT/WLnWvUjwTKPFE+hKUGm0tXSCPrNdosAgm53ZfQkpOUGNd+vlWjBqpxW4otkMAQdINKzIHQiNwuVcETB8M4sWzSiWRAIiCRJgA6a/2qvi3OYC4rEE+FgYX3jb3objj3BfU/Fueh29h/WiV/EqVAHkY5eoPTyr54qUJne0g0RAuNZQTbKgIysIJks2mgnWdRFCcAWa3dsj4xktvykFtD6amtW3DwSY1kzHMCIgdagxyrYtPCpI8R08ROgGu5Gm3lTUy0Pfb7w7B2/Kgi0uRj3JygAqp3I5ZhPPc9PKrWHxb921u0US6qyHgEsRuXLTJJ3PnWXs4h2QuEJAMMQNB0Mjar3B7F3Ei8bY+Fcupic4OityMA+WtVeGRu3Ex2Uipa4FhX8a3XRmeCXcwNtVnkNelaO3ctWlCm6pj7Frb5wPnQrhvZ5CLZZXLFcxUnVYjMpOmokCrnFcNYVAyqFlc28mInUzE1NldWabjqquAONcRa8jMFyBXKKpac3MMdOfTlQ7C4C4LYdvCDoAACWPpyq7w28rtDgKGbw7wd9p3MVa4/dRLqhs5SAVCkgKeYkbTv8qrVyh8NRUWyg+YwZh+BXbgZRKL9rN4deYJAk6cq0/HuLnE4bxEgZMrBZUtBgsJEyDJHLWhd3iB7hlt5lzOxzmTCSNQdiRIESauXWYBJi3bXSW8TE6bhfzqa3I7moo4kJphMX2lsrbuobLHL3axDExGmU/LUefnRHAYRHAz2g2ZRrlynqSrLqpED585Iq9xDD2lYsQCoBcrqIzQpGYD+aY0gAGinC8dbFsAIVUeFI8QgydCdhM0eTORjFAxaYQjkdj2lfhmKNgqLa3EIbx5lifCfGTpKnpGnWjmE7S9+7BrCuE/5BlLR1/iAnpNDOOq9u3buZDkuEr3mkBv4So5nWPQ1Tu9isUuHN+ymVlecpaHdDAzBToIOvI6mp/CXIbbYkbbylsqgC+0097BvdA7t1dcxzox8RWdADtI316UD7W4EW7wcSQ6KDO5YSFYjkd1I6qOtJg8Vdw1xExSXUdmCiSp0Om4Eb+Z0PlqT7RYC9iXFuyFJWSwY5SMqg5QI8Rby9dtaTiTJiyaTwe81mBF3MlZvlHzLB8jBGusfnzo9wm94QY0Bg66xz3/xtQJgZKwQZylWEFddjOxnfaruAxbpIUqNIObbcbef9AabmXUKEIGbO8cp3lSAQ0aEHmehq3Zs2byrcdTmHgcqxBBXQSNj+EVnuCcZF39y+h1KHlPMHyp+G7y1eusyuLTkDaFPIyTsRr51yvCcEjuP5EKrE0rW2sifiSdG8o0DDrRGzxjKoO7SAFPOfP5fOhXCOJpcDIoJVBDZtdxIEsdZq9hx3uZCjKkZZK5dR9DGmo3isxHIhtbB+XeSuVb928Ltft7wBnBDDeeu1AL2HFm4DbJKnWNZHkTVpMC6DxXIUAGT1Okb+lPxRUqQV15Hp0os3UOd2AFe33+8FFVdlNiGcLckCrINCODMMsAzFFRX0vTPqxgzmZV0uRH16mzXjVERPE1V4ngFxFm5Yf4biFfQkeFh5gwfarJpAa9PVOF/sbjf+sV6u/aV6t8L3nvEMrzSzQDs32jt4pAQYcfEp3Bo5NArAjaGykGjJAaqcTsK6EEx5jWrGamvZVt608TBsZzntHwhboABMqwYcs4UyUYdDFULF0XBctGYy5Y1DKeUdNfuFdPxGCQrGQfnzrIcV4SwJNpsrT5a+RkfWoOowE7idDB1AOxgvAYkWhGVtBBLSTtsJ5bU3vUNvvFdSZGryBLbA9NdKQm98L5VYGQGG45Q0evWongLluBSj3DbZWEgErI/PpXIdTdH89Z0hpqxzPEO9orlVLpP2TImTBMxmXz0MVb4Vgkw9tpEu+hIAEwInLr59ajNxVbLmykACIB05c9q9dxR7xVUicpIkdPu50kszDTwOZpFxnDeJZ7bsVgq2UE6gmToQDpoJ9qvYvDLFnvAMt1ZkW8ygRMHkOW9CeLIVvWnAGW4crgyM0fakbESYPOKXivE2GHaz9hVMdVB0An3q5CljbkbfO4t1LG1mQ43fRrmS0FhT/uICAdwVUf12p11mCq7A55y52JJiPPbTpFDVa8jZQssvhmBCjy5D+9X+z+De9cJuksgPig78o9BXSKqou+JOHN1KOOu3LuVZhV5jT6iJPn7Vfwr4hfEumTQZvFBIjfz/rW0vcIwluLj/DlJA1GYgaJoYE/0rIX1ZmCqdwTl/pSTn1bARYZddG4mD4i/eNcZzbd9Dpm2BEAchPTrRPBvecZLYDggZhGxJ8JJXZY59VM0Gu4R7ii3kI8XxFSI85PlRnhCDDODaushjKwIzqwbyO2vtWM6jn/codX2K9oVs4y7bC2bkDUMVZduhEaFoMj08603BeKtG866anxCYJHT0rm3EcNeLXHt3IOkA/ZB5Dw+EdOlO4ZiXtrauW7h7xGIdfsvtrvJO/IRpvU2TBqGtGFjjmGAXGll5nQe1fZ5cXBDFXUBbZGpBlj8PMa669NoqDG8Oc4gupAuwo0MElQAo1MA7DfY1BwHtH3zG0QFvGQstHh3JUnn5b6Vew/B7AVm74qcuY3J0G/h1Opqe8oQK39zyAYybPHtcF9oODteKsyqjQAbhBUn+W4u+muoBrOYfh7JcZbkW1X4i0EHzUnQiNj51p8RxwIot3znUbFhlYE6eGNR16UJ4kFuIwdOchvCCSdQQykgg6/UU5HYLVGuPlHKh+sel3DRcu2Q+YqUDHS2BzKrEgDf/NWuFYu5fQJcgsIYEAEEQIZlYgzuDGutTcIwCXMNd7u5lVlyOTE21BDMB0BjU9PShn7LX1Yvh8ZaIZMg0IhTEgET0Gu9Y4RhuaPYmK1FWqHbN8WiAFIzSVJ0zZQNSdfLWrtvH4hlDlSF5gMr6b6lT4fcVjm4RiCmQvmv2YKsLg0XTSSdJ6GJijfBcW+GZFkkvaDvK+DMS0p4ZynTnWHEgFFtz6f5qawBFgC/eaDE4hmsyBmCkEAR1316E1Dh7t1TneEWYBkEmeYHWohjozxbQTvkckDr4NfkCNqn/U++yZDMqCH18OvihTI5ee+9Tr05Y1ye3MTQU3wPpD/Dk8IMROtXxUOGs5VCyTA3Jkn1NTV9Hgx+GgWcnI2piYs16kr1Oi4hpDSmo799baNccwiKXYnYKokn5CvT0s5q9XHP/wCw3f8AoP0/Glo4FTM4DGvaYXLZIdfr1BHPr7+VdQ7MdtkvBUukK526H0P53rkoadQdRuPz9f8ANPVwOsE6Ry56efPzFQixxLtiKM+iUcESDNOmuN9nu2l7DkJcOdY0POPLrXSeDdpbGIEq4nmDoR6inLk9YhsZHEOzUV7DI/xKDShqdmpnMXxAfFOBK4hV09dvTpQHivZq+LbBQGEqyhdGBEbg77bjrtW8DUoap36VHN95QnUunE41xnC3Wa22ZreQDOQAwJHIg0Y4bkbUEFsuXN5c5roOO4Vau+JlGbkw0Pv196zWJ7O3hmyqpHRQFJH9T8q5fV9Hkql3+UvxdZjYUdjAuNtLKZijlTmykttqJHnsaqcVw1s22KKVzJlYliwmNDrtUvG0NsC7dRlKwsEEaEwII33qbCp+7ZTbIDAiGIIEjcVGNaKLlOoEXcxfE75ez4BLRDdQRoat9kmVCNWVguqnVCuo9vvqTCcGud8/dL3gVCzgEaAHQ5Z1O9R4Xhl1C960gZLmpE/D1LAe9dMMCtD5iAVs8zUXriOBorQZAMEA1Sv2bMg6Kx21+cVn8Dbzl1uW4IX411UzMa9aXhGFDDI10kqfCoBDR1g70grV3PBKO0M4k3CABBX7XVekdRVXD2wjjvBDGSsncA75elTcPvrbu5QAzP4SzFfD6z8PyqPi3C0usbn6wAQCBuw02COp0HnSlF7NsPWNbbiSYzD5xcK6BlExzg7D2qhglXMM3MgT6RrTEuXVlWuZhGwGg9969wxLZvEgljuSTp6L0FMqlI/xGDtcudo+CYgtavYYZluE2gBoVcHwtPz+RrX27Jw9k22UPeCMxEblV1yjX006ip8H3tu2hUZ0kSB4mRp0JA5a70M7U44m+sWmkIoVo8RJJnL1iaJnGgKRuOP7kR1Nkq9v9TP8XwVu9aw99y2ZwRcUGJYZiW1+R9BtRiwcI9u2lxstsXMwzGCCFygTOinc/wDlVjtJw1UsqJJMEgkAeIa8tiRXOLOGVroDZiGIgA+IMTGUSNTy96dhLHysajbVlBE6bwPH2RdPcqcrSMpjxidJHl16VDjlwuEc9zbCF2WSpIRco1AG358qqcFCJeuW3zeF8gIOpkkxO4+m9F+1+FnDIqFLSJdDCDqdG1n3PWgUeVrb6RDH/wCg25gRMtxlcOA5uINWklZ8em5AAmOWlGhhw6Sko2sgGJ1O+vMfeKG909qBcRnJ2yhGLf8AiBrWhwvA7txgzN3duNVIGc+XQffU5wtkICA2PzmObKEFk0IHThTF0ZLUtBh8oG41JKnxb7mtfwfhvcoFJk6mYgCTMAdKv2rQUQPmd/c08118HTDHuTZnMz9Wcg0jYRK9Xq9VMlnqSa8TTa9PR01zz9K3GmKpgLGrXWAusNuRFqepME+XrW04nxAW1dUINxV8R3CTsPNvurIm1mtWFOW4WfPJ0mTmJnmYn5UnJmC7CNx4i25nPP2Kxn/V9RXq7H3idRS0n4lo3wVnDc2s/aG88x+O89fnTg/SIjUdNfxO/t6tU65TvpBH0/P3U7KZEbn5Hr/jb7qOYIgPImQZynpO/wDcfktW8yMCrFXAkMCIPTyPrzpQNNBz1U7es/k+teiRrqByO4Ma+X9DWzZqOCfpGvWfDeGYDnz+Vb/gvbXDYjZwD0Oh+VcQv2pHlyPMAfn+9ULoywwJGkhgYPr1pirfEUx9Z9Q2rytqCDUoNfOfCe2eKwxAz5l00at/wP8ASjbeFvIVO0gSPpReYcwaB4M6eGpQ9CMDxq3dAKk6+RogrzWhgYJWuZPcRXEMAw6ESKEY/szh7oIhkPVGK/Tb6UTD0uavMqt+4XMUsp8pqYvDdhruHu99hb4DdHmGB5NHL2q2vCLqPn7qJ+IIQVM76Vqs1ODUHw6HaNPUZO8wFvvbDMBZYIGP2dCJ+tXLD2vjVFS4dyBHzraTUdzDo26KfUCocn6SDZVuY8dff7l+0zWJOFvKbeJVGjmQNCRurbiud8ZwRtt3aOndcmDkkDzBrsF3guGbeynyj7qGYnsVgX3s/JnH3GmDo8ukKxG3zh4+sRCSL3nOOHYVLzAPeCggDWACOW/Wo04Yli+yqcxmRtqJ0rpK9h8ECCLbafzt+NWD2UwufP3ZzRlnM2w/zWfB5KqxHt1+K7F/aZHhuMW24Yu2aIyq0D1brRxsULrKxGvwhjqFnzoxb7N4Uf8AGNTJkk71cscMsoIVAB8/vqc/p3UHYMAPz2iG63GTek3OdYzE3pZbytlB1Yg5Ogg8qbwng4TEW7yWXdSSRllgCQdWnT3rp3dqNlHyFOqnH0DAedt/b/2Y3XiqVZhrfY0tcLxkzNmbX4iddaMt2UtOQbzu8CMswtaCmlqeOkxA2d4hurynYGpWwPDrVkRaQKOcDU+p3NWCaQmmk1RQHEnsncx00hamTSE1lz1R5NNmort0KJJ0oU/G85y2RJ18TaDTy3NAzAcw1UniGLlwKCzEADmaC47jfgYocqjw5joWMTp0WDQq5fN4nOx+DNPSCBKjYCTtH9lN8/7sjKAQEImTtuNhoZ38qmfPeyyhMNbmNYnKLOdQ7SSY3iSNN9qlzKbmeFyWxA02bkQPf61D3pBMkF7ihlbL8KkNJ9dG+VQXAHy2+sh2XTxKpBMnXSDy61NcdH9+38J/9TXqTvLf/wAn/sPwr1ZMn//Z");
                    break;
            }
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
    }

    public void removeLastItem(View view) {
        if (adapter.getCount() - 1 >= 0)
            adapter.deleteItem(adapter.getCount() - 1);
    }

    public void addNewItem(View view) {
        SliderItem sliderItem = new SliderItem();
        sliderItem.setDescription("Slider Item Added Manually");
        sliderItem.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
        adapter.addItem(sliderItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        cartUpdate();
        MenuItem item = menu.findItem(R.id.action_addcart);
        MenuItemCompat.setActionView(item, R.layout.cart_count_layout);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);
        tv = notifCount.findViewById(R.id.hotlist_hot);
        View view = notifCount.findViewById(R.id.hotlist_bell);

        cartUpdate();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(myIntent);
        }});

        return true;
    }

    public static void cartUpdate() {
        if (tv != null && cartFoods != null)
            tv.setText(Integer.toString(cartFoods.size()));
    }


    private void msg() {
        Toast msg = Toast.makeText(MainActivity.this,"Item Added To cart",Toast.LENGTH_SHORT);
    }

    @Override
    protected void onResume() {
        invalidateOptionsMenu();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

