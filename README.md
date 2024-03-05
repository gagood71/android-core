# android-core

<p>基於原生 androidx.appcompat:appcompat 建構類似 MVC 架構。</p>
<p>M 對應到 Response</p>
<p>V 對應到 CompatActivity</p>
<p>C 對應到 Controller</p>

## Response

為基礎的 Model，可針對不同 RESTful API 自訂義格式內容，以下為範例：

```java
public class LoginResponse extends Response {
    public int code; // 狀態碼
    public Body body; // 內容
    
    public class Body {
        //...
    }
}
```

## DefaultActivity

<p>為基礎的 Activity，並繼承 AppCompatActivity。</p>

## CompatActivity

<p>繼承 DefaultActivity，可賦予一個 ActivityController 類型的 Controller，以下為範例：</p>

```java
public abstract class CompatActivity<T extends ActivityController>
        extends DefaultActivity {
    protected T controller;
    
    //...

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //...

        try {
            //...

            if (getViewController() != null) { // 檢查是否賦予 Controller
                controller = getViewController().newInstance(); // 進行 Controller 實例化
                
                // ...
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //...
}
```

<p>基於 Dialog 建立需要 Activity，在 onDestroy 生命週期中，追加釋放空間動作。</p>
<p>並搭配 Controller 處理 onActivityResult、onRequestPermissionsResult 請求事件。</p>

## Controller

<p>為基礎的 Controller，延伸出以下不同類型：</p>

1. ActivityController（需搭配 CompatActivity）
2. DialogController（需搭配 CompatDialog）
3. FragmentController（需搭配 CompatFragment）

<p>根據不同原生 UI 元件，會延伸出不同 Controller，兩者關係是綁定的（如果有賦予），並隨著 UI 元件生命週期，執行對應事件處理。</p>

## ActivityController

<p>在 CompatActivity 進行 onCreate 生命週期時，檢查是否有賦予對應 Controller， 如果有則會實例化 Controller。</p>