package com.ble;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import com.ble.R;
import com.zxing.activity.CaptureActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	public class DataClass {
		public BluetoothDevice device = null;
		public String name;
		public Integer rssi = 0;
		public int count = 0;
		public String address;
		public String version = "";
	}

	private DataClass m_myData = new DataClass();
	
//	private StringBuffer sb = new StringBuffer();

	BluetoothAdapter mBluetoothAdapter;
	BluetoothGattCharacteristic writeCharacteristic;
	BluetoothGattCharacteristic readCharacteristic;
	BluetoothGatt mBluetoothGatt;

	//byte[] key = {58,96,67,42,92,01,33,31,41,30,15,78,12,19,40,37};
	byte[] key = { 32, 87, 47, 82, 54, 75, 63, 71, 48, 80, 65, 88, 17, 99, 45, 43 };
	 byte[] key2 = { 32, 87, 47, 82, 54, 75, 63, 71, 48, 80, 65, 88, 17, 99, 45, 43 };
	// byte[] key2 = {66,66,66,66,88,88,88,99,48,55,55,88,55,99,55,55};
	byte[] mima = { 0x30, 0x30, 0x30, 0x30, 0x30, 0x30 };
	byte[] mima2 = {0x32, 0x32, 0x32, 0x32, 0x32, 0x32};

	public static final ParcelUuid findServerUUID = ParcelUuid
			.fromString("0000fee7-0000-1000-8000-00805f9b34fb");
	public static final UUID OAD_SERVICE_UUID = UUID
			.fromString("f000ffc0-0451-4000-b000-000000000000");

	public static final UUID bltServerUUID = UUID
			.fromString("0000fee7-0000-1000-8000-00805f9b34fb");
	public static final UUID readDataUUID = UUID
			.fromString("000036f6-0000-1000-8000-00805f9b34fb");

	public static final UUID CLIENT_CHARACTERISTIC_CONFIG = UUID
			.fromString("00002902-0000-1000-8000-00805f9b34fb");

	public static final UUID writeDataUUID = UUID
			.fromString("000036f5-0000-1000-8000-00805f9b34fb");

	byte[] token = new byte[4];

	byte[] gettoken = { 0x06, 0x01, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

	volatile String m_nowMac = "";

	TextView tvOADtishi;
	TextView tvState;
	TextView tvOpenTime;
	TextView info;
	Button buttonOpen;
	Button buttonReset;
	Button buttonElectricity;
	Button buttonScanCode;
	Button buttonSetHuancheState;
	Button buttonSearchLockModel;
	Button buttonSetLockModel;
	Button buttonSearchLockWorkState;
	Button buttonGetToken;
	Button buttonSearchGIM_ID;
	Button buttonSearchGSMVersion;
	Button buttonKongzhiState;
	RadioButton rbTonnxunSet;
	RadioButton rbzhuangtaiSearch;
	
	TableRow trSet;
	TableRow trSearch;
	TableRow trSearch1;
	LinearLayout llSet;
	LinearLayout llSeearchOne;
	LinearLayout llSeearchOnes;
	LinearLayout llSeearchOness;
	
	
	RadioGroup rgModel = null;  
	RadioButton rbZhengchang = null;  
	RadioButton rbYunshu = null; 
	RadioButton rbChongqi = null;  

	Button buttonOAD;
	Button buttonUpgrade;

	TextView updateInfo;
	TextView updateInfo1;
	TextView updateInfo2;
	TextView updateInfo3;
	TextView updateInfo4;
	TextView updateInfo5;
	TextView updateInfo6;
	TextView updateInfo7;
	TextView updateInfo8;
	TextView updateInfo9;
	TextView updateInfo10;
	TextView updateInfo11;
	TextView updateInfo12;
	TextView updateInfo13;
	TextView updateInfo14;
	TextView updateInfo15;
	TextView updateInfo16;
	TextView updateInfo17;
	TextView updateInfo18;
	TextView updateInfo19;
	TextView updateInfo20;
	TextView updateInfo21;
	TextView updateInfo22;
	TextView updateInfo23;
	TextView updateInfo24;
	TextView updateInfo25;
	TextView updateInfo26;
	TextView updateInfo27;
	TextView updateInfo28;
	TextView updateInfo29;
	TextView updateInfo30;
	TextView updateInfo31;
	TextView updateInfo32;
	TextView updateInfo33;
	TextView updateInfo34;
	private ProgressBar mProgressBar;
	private String openTime;

	Button buttonModifyPassword;
	Button buttonModifyKey;

	Button buttonSearchState;
	Button buttonNewKey;
	
	Button buttonShouQuan;
	
	Button buttonGetKey;

	List<ScanFilter> bleScanFilters;
	ScanSettings bleScanSettings;
	BluetoothLeScanner mBluetoothLeScanner;
	TextView proInfo;
	Button buttonRestartBle;

	private static final int CAOZUO_OPEN = 1;
	private static final int CAOZUO_CLOSE = 2;
	private static final int CAOZUO_DIANLIANG = 3;
	private static final int CAOZUO_MIMA = 4;
	private static final int CAOZUO_MIYUE = 5;
	private static final int CAOZUO_OAD = 6;
	private static final int CAOZUO_ZHUANGTAI = 7;
	private static final int CAOZUO_HUANCHESTATE = 8;
	private static final int CAOZUO_SEARCHLOCKMODEL = 9;
	private static final int CAOZUO_SETLOCKMODEL = 10;
	private static final int CAOZUO_SEARCHLOCKWORKSTATE = 11;
	private static final int CAOZUO_KONGZHISTATE = 12;
	private static final int CAOZUO_GETTOKEN = 12;
	private static final int CAOZUO_GIM_ID = 13;
	private static final int CAOZUO_GSM_VER = 14;
	private int caozuo = 0;

	// Activity
	private static final int FILE_ACTIVITY_REQ = 0;

	// Programming parameters
	private static final short OAD_CONN_INTERVAL = 12; // 15 milliseconds
	private static final short OAD_SUPERVISION_TIMEOUT = 50; // 500 milliseconds
	private static final int GATT_WRITE_TIMEOUT = 300; // Milliseconds

	private static final int FILE_BUFFER_SIZE = 0x40000;
	private static final String FW_CUSTOM_DIRECTORY = Environment.DIRECTORY_DOWNLOADS;
	private static final String FW_FILE_A = "SensorTagImgA.bin";
	private static final String FW_FILE_B = "SensorTagImgB.bin";

	private static final int OAD_BLOCK_SIZE = 16;
	private static final int HAL_FLASH_WORD_SIZE = 4;
	private static final int OAD_BUFFER_SIZE = 2 + OAD_BLOCK_SIZE;
	private static final int OAD_IMG_HDR_SIZE = 8;
	private static final long TIMER_INTERVAL = 1000;

	private static final int SEND_INTERVAL = 20; //20 Milliseconds (make sure this
													// is longer than the
													// connection interval)
	private static final int BLOCKS_PER_CONNECTION = 4; // May sent up to four
														// blocks per connection

	// Programming
	private final byte[] mFileBuffer = new byte[FILE_BUFFER_SIZE];
	private final byte[] mOadBuffer = new byte[OAD_BUFFER_SIZE];
	private ImgHdr mFileImgHdr = new ImgHdr();
	private ProgInfo mProgInfo = new ProgInfo();

	// Housekeeping
	private boolean mServiceOk = false;
	private boolean mProgramming = false;

	private volatile boolean mBusy = false; // Write/read pending response

	private volatile boolean m_xinMiYue = false;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//��ȡ�����豸
		getBleDevice();
		//��ʼ����ͼ
		initView ();
		
		setLockModel();
		
	}
	
	
	//��ȡ�����豸
	private void getBleDevice(){
		
		if (!getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_BLUETOOTH_LE)) {
			Toast.makeText(this, "�����豸��֧������4.0", Toast.LENGTH_SHORT).show();
			finish();
		}

		BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

		mBluetoothAdapter = bluetoothManager.getAdapter();

		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "��ȡ����ʧ��", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}

		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, 188);
		} else {
			mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();

			bleScanFilters = new ArrayList<>();
			bleScanFilters.add(new ScanFilter.Builder().setServiceUuid(
					findServerUUID).build());

			bleScanSettings = new ScanSettings.Builder().build();
			mBluetoothLeScanner.startScan(null, bleScanSettings, scanCallback);
		}

	}
	
	//��ʼ����ͼ
		private void initView (){
			
			tvOADtishi = (TextView) findViewById(R.id.textView2);
			tvState =(TextView) findViewById(R.id.state);
			tvOpenTime =(TextView) findViewById(R.id.openTime);
			info = (TextView) findViewById(R.id.textView);
			buttonOpen = (Button) findViewById(R.id.openOrCloseLock);
			buttonReset = (Button) findViewById(R.id.reset);
			buttonElectricity = (Button) findViewById(R.id.electricity);
			buttonScanCode = (Button) findViewById(R.id.scanCode);
			buttonSetHuancheState = (Button) findViewById(R.id.setHuanche);
			buttonSearchLockModel =(Button) findViewById(R.id.searchLockModel);
			buttonSetLockModel = (Button) findViewById(R.id.setLockModel);
			buttonSearchLockWorkState = (Button) findViewById(R.id.searchLockWorkState);
			buttonGetToken = (Button) findViewById(R.id.getToken);
			buttonSearchGIM_ID = (Button) findViewById(R.id.searchGIM_ID);
			buttonSearchGSMVersion = (Button) findViewById(R.id.searchGSM_version);
			buttonKongzhiState = (Button) findViewById(R.id.btnKongzhiState);
			rbTonnxunSet = (RadioButton) findViewById(R.id.tongxunSet);
			rbzhuangtaiSearch = (RadioButton) findViewById(R.id.zhuangtaiSearch);
			rgModel = (RadioGroup) findViewById(R.id.setModleLayout);
			rbZhengchang = (RadioButton) findViewById(R.id.zhengchangModel);
			rbChongqi = (RadioButton) findViewById(R.id.chongqiSet);
			rbYunshu = (RadioButton) findViewById(R.id.yunshuModel);
			llSet = (LinearLayout) findViewById(R.id.set_tv_layout);
			llSeearchOne = (LinearLayout) findViewById(R.id.ll_search_one);
			llSeearchOnes = (LinearLayout) findViewById(R.id.ll_search_ones);
			llSeearchOness = (LinearLayout) findViewById(R.id.ll_search_oness);

			buttonOAD = (Button) findViewById(R.id.OAD);
			buttonUpgrade = (Button) findViewById(R.id.upgrade);
			buttonRestartBle = (Button) findViewById(R.id.restartBle);
			updateInfo = (TextView) findViewById(R.id.textView1);
			updateInfo1 = (TextView) findViewById(R.id.textView2);
			updateInfo2 = (TextView) findViewById(R.id.textView3);
			updateInfo3 = (TextView) findViewById(R.id.textView4);
			updateInfo4 = (TextView) findViewById(R.id.textView5);
			updateInfo5 = (TextView) findViewById(R.id.textView6);
			updateInfo6 = (TextView) findViewById(R.id.textView7);
			updateInfo7 = (TextView) findViewById(R.id.textView8);
			updateInfo8 = (TextView) findViewById(R.id.textView9);
			updateInfo9 = (TextView) findViewById(R.id.textView10);
			updateInfo10 = (TextView) findViewById(R.id.textView11);
			updateInfo11 = (TextView) findViewById(R.id.textView12);
			updateInfo12 = (TextView) findViewById(R.id.textView13);
			updateInfo13 = (TextView) findViewById(R.id.textView14);
			updateInfo14 = (TextView) findViewById(R.id.textView15);
			updateInfo15 = (TextView) findViewById(R.id.textView16);
			updateInfo16 = (TextView) findViewById(R.id.textView17);
			updateInfo17 = (TextView) findViewById(R.id.textView18);
			updateInfo18 = (TextView) findViewById(R.id.textView19);
			updateInfo19 = (TextView) findViewById(R.id.textView20);
			updateInfo20 = (TextView) findViewById(R.id.textView21);
			updateInfo21 = (TextView) findViewById(R.id.textView22);
			updateInfo22 = (TextView) findViewById(R.id.textView23);
			updateInfo23 = (TextView) findViewById(R.id.textView24);
			updateInfo24 = (TextView) findViewById(R.id.textView25);
			updateInfo25 = (TextView) findViewById(R.id.textView26);
			updateInfo26 = (TextView) findViewById(R.id.textView27);
			updateInfo27 = (TextView) findViewById(R.id.textView28);
			updateInfo28 = (TextView) findViewById(R.id.textView29);
			updateInfo29 = (TextView) findViewById(R.id.textView30);
			updateInfo30 = (TextView) findViewById(R.id.textView31);
			updateInfo31 = (TextView) findViewById(R.id.textView32);
			updateInfo32 = (TextView) findViewById(R.id.textView33);
			updateInfo33 = (TextView) findViewById(R.id.textView34);
			updateInfo34 = (TextView) findViewById(R.id.textView35);
			mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
			mProgressBar.setProgress(0);
			proInfo = (TextView) findViewById(R.id.textViewPro);

			buttonModifyPassword = (Button) findViewById(R.id.modifyPassword);
			buttonModifyKey = (Button) findViewById(R.id.modifyKey);

			buttonSearchState = (Button) findViewById(R.id.searchState);
			buttonNewKey = (Button) findViewById(R.id.newKey);

			buttonShouQuan = (Button) findViewById(R.id.weixinShouquan);
			buttonGetKey = (Button) findViewById(R.id.getKey);
			
			trSet = (TableRow) findViewById(R.id.tr_set);
			trSearch = (TableRow) findViewById(R.id.tr_search);
			trSearch1 = (TableRow) findViewById(R.id.tr_search1);
			
			buttonOpen.setOnClickListener(this);
			buttonReset.setOnClickListener(this);
			buttonElectricity.setOnClickListener(this);
			buttonScanCode.setOnClickListener(this);
			buttonOAD.setOnClickListener(this);
			buttonUpgrade.setOnClickListener(this);
			buttonRestartBle.setOnClickListener(this);
			buttonModifyPassword.setOnClickListener(this);
			buttonModifyKey.setOnClickListener(this);
			buttonSearchState.setOnClickListener(this);
			buttonNewKey.setOnClickListener(this);
			buttonShouQuan.setOnClickListener(this);
			buttonGetKey.setOnClickListener(this);
			buttonSetHuancheState.setOnClickListener(this);
			buttonSearchLockModel.setOnClickListener(this);
			buttonSetLockModel.setOnClickListener(this);
			buttonSearchLockWorkState.setOnClickListener(this);
			buttonGetToken.setOnClickListener(this);
			buttonSearchGIM_ID.setOnClickListener(this);
			buttonSearchGSMVersion.setOnClickListener(this);
			rbTonnxunSet.setOnClickListener(this);
			rbzhuangtaiSearch.setOnClickListener(this);
			buttonKongzhiState.setOnClickListener(this);
			
			
		}

		
	   @Override
	    public void onClick(View v) {

	        switch (v.getId()) {
	            //������
	            case R.id.openOrCloseLock:
	            	openCloseNock();
	                break;
	            //��λ
	            case R.id.reset:
	            	reset();
	                break;
	            //���� ��ѯ   
	            case R.id.electricity:
	            	electricity();
	                break;
	            //ɨ���ϴ�
	            case R.id.scanCode:
	            	scanCode();
	                break;
	            //OAD
	            case R.id.OAD:
	            	OAD();
	                break;
	           //��ȡ��Կ
	            case R.id.getKey:
	            	getKey();
	                break;
	            //��������
	            case R.id.restartBle:
	            	restartBle();
	                break;
	            //�޸�����
	            case R.id.modifyPassword:
	            	modifyPassword();
	                break;
	            //�޸���Կ
	            case R.id.modifyKey:
	            	modifyKey();
	                break;
	            //��ѯ��Դ����״̬
	            case R.id.searchState:
	            	searchState();
	            	llSeearchOnes.setVisibility(View.VISIBLE);
	            	llSeearchOness.setVisibility(View.GONE);
	                break;
	            //�¿�����Կ
	            case R.id.newKey:
	            	newKey();
	                break;
	            //΢����Ȩ
	            case R.id.weixinShouquan:
	            	shouQuan();
	                break;
	              //�����豸
	            case R.id.upgrade:
	            	upgrade();
	                break;
	              //���û���״̬
	            case R.id.setHuanche:
	            	setHuancheState();
	                break;
	              //��ѯ���״̬��
	            case R.id.searchLockModel:
	            	searchLockModel();
	            	llSeearchOnes.setVisibility(View.VISIBLE);
	                break;
	                //�������Ĺ���ģʽ
	            case R.id.setLockModel:
//	            	setLockModel();
	                break;
	                //��ѯ���Ĺ���״̬
	            case R.id.searchLockWorkState:
	            	searchLockWorkState();
	            	llSeearchOness.setVisibility(View.GONE);
	            	llSeearchOnes.setVisibility(View.VISIBLE);
	                break;
	                //��ѯ����GSM_ID��
	            case R.id.searchGIM_ID:
	            	searchGIM_ID();
	            	llSeearchOnes.setVisibility(View.VISIBLE);
	            	llSeearchOness.setVisibility(View.GONE);
	                break;
	                //��ѯ����GSM�汾��
	            case R.id.searchGSM_version:
	            	searchGSM_Ver();
	                break;
	                //������״̬
	            case R.id.btnKongzhiState:
	            	KongzhiState();
	            	llSeearchOnes.setVisibility(View.GONE);
	            	llSeearchOness.setVisibility(View.VISIBLE);
	                break;
	                //�ײ�ͨѶ���ð�ť
	            case R.id.tongxunSet:
	            	trSearch.setVisibility(View.GONE);
	            	trSearch1.setVisibility(View.GONE);
	            	trSet.setVisibility(View.VISIBLE);
	            	rgModel.setVisibility(View.VISIBLE);
	            	llSet.setVisibility(View.VISIBLE);
	            	llSeearchOne.setVisibility(View.GONE);
	                break;
	              //�ײ�ͨ״̬��ѯ��ť
	            case R.id.zhuangtaiSearch:
	            	trSet.setVisibility(View.GONE);
	            	rgModel.setVisibility(View.GONE);
	            	trSearch.setVisibility(View.VISIBLE);
	            	trSearch1.setVisibility(View.VISIBLE);
	            	llSet.setVisibility(View.GONE);
	            	llSeearchOne.setVisibility(View.VISIBLE);
	                break;
	                
	        }
	    }
	   StringBuffer sb1;
	   private void setLockModel(){
		   
			//δ���ü���ʱ��ʾĬ��ѡ������  
	        if(rbZhengchang.getId() == rgModel.getCheckedRadioButtonId()){  
					updateInfo.append(openTime+"  �������: ����ģʽ���óɹ���\r\n");
	        }else if(rbYunshu.getId() == rgModel.getCheckedRadioButtonId()){
					updateInfo.append(openTime+"  ������: ���ģʽ���óɹ���\r\n");
	        } else if(rbChongqi.getId() == rgModel.getCheckedRadioButtonId()){  
//					updateInfo.append(openTime+"  ����ģʽ���ã�\r\n");
	        	
	        }   
	        //ΪRadioGroup���ü����¼�  
	        rgModel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {  
	              
	            @Override  
	            public void onCheckedChanged(RadioGroup group, int checkedId) {  
	            	
	            	if (m_myData.device == null) {
     					Toast toast = Toast.makeText(MainActivity.this,
     							"û���豸������ɨ��", Toast.LENGTH_SHORT);
     					toast.setGravity(Gravity.CENTER, 0, 0);
     					toast.show();
     					return;
     		
			}
			
			mBluetoothLeScanner.stopScan(scanCallback);
 			caozuo = CAOZUO_SETLOCKMODEL;
 			
 			if (mBluetoothGatt != null && writeCharacteristic != null) {
					if (mBluetoothGatt.getDevice().getAddress()
							.equals(m_myData.address)) {
						sb1 = new StringBuffer();
		                if(checkedId == rbZhengchang.getId()){  
		                	
		                	byte[] zhengchangModle = { 0x05, 0x21, 0x01, 0X00, token[0],
									token[1], token[2], token[3], 0x00, 0x00, 0x00,
									0x00, 0x00, 0x00, 0x00, 0x00 };
								getTime();
//								updateInfo3.append(openTime+"  ����״̬���óɹ���\r\n");
								sb1.append(openTime+"  ����״̬���óɹ���\r\n");
//								updateInfo3.setText(sb1.toString());
							SendData(zhengchangModle);
							
		                }else if(checkedId == rbYunshu.getId()){
		                	
		                	byte[] yunshuModle = { 0x05, 0x21, 0x01, 0X01, token[0],
									token[1], token[2], token[3], 0x00, 0x00, 0x00,
									0x00, 0x00, 0x00, 0x00, 0x00 };
							getTime();
//								updateInfo4.append(openTime+"  ���״̬���óɹ���\r\n");
								sb1.append(openTime+"  ���״̬���óɹ���\r\n");
//								updateInfo4.setText(sb1.toString());
							SendData(yunshuModle);
		                }else if(checkedId == rbChongqi.getId()){  
		                	
		                	byte[] chongqiModle = { 0x05, 0x21, 0x01, 0X02, token[0],
									token[1], token[2], token[3], 0x00, 0x00, 0x00,
									0x00, 0x00, 0x00, 0x00, 0x00 };
								getTime();
								updateInfo.append(openTime+"  ����ģʽ���ã�\r\n");
							SendData(chongqiModle);
		                }  
		                
		                updateInfo4.setText(sb1.toString());
						
					} else {
						mBluetoothGatt.disconnect();
						mBluetoothGatt.close();
						mBluetoothGatt = null;

						mBluetoothGatt = m_myData.device.connectGatt(
								MainActivity.this, false, mGattCallback);
					}
				} else {
					mBluetoothGatt = m_myData.device.connectGatt(
							MainActivity.this, false, mGattCallback);
				}
 			
	            }  
	            
	        });  
		}
	   

	//�������ķ���
	   private void openCloseNock(){
		   
		   if (m_myData.device == null) {
				Toast toast = Toast.makeText(MainActivity.this,
						"û���豸������ɨ��", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}

			if (m_xinMiYue) {
				Toast toast = Toast.makeText(MainActivity.this, "ֻ��������Կ����",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}

			mBluetoothLeScanner.stopScan(scanCallback);
			caozuo = CAOZUO_OPEN;

			if (mBluetoothGatt != null && writeCharacteristic != null) {
				if (mBluetoothGatt.getDevice().getAddress()
						.equals(m_myData.address)) {
					byte[] openLock = { 0x05, 0x01, 0x06, mima[0], mima[1],
							mima[2], mima[3], mima[4], mima[5], token[0],
							token[1], token[2], token[3], 0x00, 0x00, 0x00 };
					SendData(openLock);
				} else {
					mBluetoothGatt.disconnect();
					mBluetoothGatt.close();
					mBluetoothGatt = null;
					mBluetoothGatt = m_myData.device.connectGatt(
							MainActivity.this, false, mGattCallback);
					
				}
			} else {
				mBluetoothGatt = m_myData.device.connectGatt(
						MainActivity.this, false, mGattCallback);
			}
	   }
	   
	   //��λ�ķ���
	   private void reset(){
		   
		   if (m_myData.device == null) {
				Toast toast = Toast.makeText(MainActivity.this,
						"û���豸������ɨ��", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}

			mBluetoothLeScanner.stopScan(scanCallback);
			caozuo = CAOZUO_CLOSE;

			if (mBluetoothGatt != null && writeCharacteristic != null) {
				if (mBluetoothGatt.getDevice().getAddress()
						.equals(m_myData.address)) {
					byte[] closeLock = { 0x05, 0x0c, 0x01, 0x01, token[0],
							token[1], token[2], token[3], 0x00, 0x00, 0x00,
							0x00, 0x00, 0x00, 0x00, 0x00 };
					SendData(closeLock);
				} else {
					mBluetoothGatt.disconnect();
					mBluetoothGatt.close();
					mBluetoothGatt = null;

					mBluetoothGatt = m_myData.device.connectGatt(
							MainActivity.this, false, mGattCallback);
				}
			} else {
				mBluetoothGatt = m_myData.device.connectGatt(
						MainActivity.this, false, mGattCallback);
			}
	   }
	   
	   //������ѯ�ķ���
	   private void electricity(){
		   
		   if (m_myData.device == null) {
				Toast toast = Toast.makeText(MainActivity.this,
						"û���豸������ɨ��", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}

			mBluetoothLeScanner.stopScan(scanCallback);
			caozuo = CAOZUO_DIANLIANG;

			if (mBluetoothGatt != null && writeCharacteristic != null) {
				if (mBluetoothGatt.getDevice().getAddress()
						.equals(m_myData.address)) {
					byte[] dianLiang = { 0x02, 0x01, 0x01, 0x01, token[0],
							token[1], token[2], token[3], 0x00, 0x00, 0x00,
							0x00, 0x00, 0x00, 0x00, 0x00 };
					SendData(dianLiang);
				} else {
					mBluetoothGatt.disconnect();
					mBluetoothGatt.close();
					mBluetoothGatt = null;

					mBluetoothGatt = m_myData.device.connectGatt(
							MainActivity.this, false, mGattCallback);
				}
			} else {
				mBluetoothGatt = m_myData.device.connectGatt(
						MainActivity.this, false, mGattCallback);
			}
		   
	   }
	   
	
	   
	   //ɨ���ϴ��ķ���
	   private void scanCode(){
		   
		   if (m_myData.device == null) {
				Toast toast = Toast.makeText(MainActivity.this,
						"û���豸������ɨ��", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}

			mBluetoothLeScanner.stopScan(scanCallback);

			m_nowMac = m_myData.address;
			// ��ɨ�����ɨ����������ά��
			Intent openCameraIntent = new Intent(MainActivity.this,
					CaptureActivity.class);
			startActivityForResult(openCameraIntent, 288);
	   }
	   //OAD����
	   private void OAD(){
		   
		   if (m_myData.device == null) {
				Toast toast = Toast.makeText(MainActivity.this,
						"û���豸������ɨ��", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}

			mBluetoothLeScanner.stopScan(scanCallback);

			if (!checkFile()) {
				updateInfo.append("Download���Ҳ��������ļ�upFile.bin\n");
				return;
			}

			caozuo = CAOZUO_OAD;
			tvOADtishi.setVisibility(View.VISIBLE);
			if (mBluetoothGatt != null && writeCharacteristic != null) {
				if (mBluetoothGatt.getDevice().getAddress()
						.equals(m_myData.address)) {
					byte[] oad = { 0x03, 0x01, 0x01, 0x01, token[0],
							token[1], token[2], token[3], 0x00, 0x00, 0x00,
							0x00, 0x00, 0x00, 0x00, 0x00 };
					SendData(oad);
				} else {
					mBluetoothGatt.disconnect();
					mBluetoothGatt.close();
					mBluetoothGatt = null;

					mBluetoothGatt = m_myData.device.connectGatt(
							MainActivity.this, false, mGattCallback);
				}
			} else {
				mBluetoothGatt = m_myData.device.connectGatt(
						MainActivity.this, false, mGattCallback);
			}
	   }
	   
	   //�����ķ���
	   private void upgrade(){
		   if (m_myData.device == null) {
				Toast toast = Toast.makeText(MainActivity.this,
						"û���豸������ɨ��", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}

			mBluetoothLeScanner.stopScan(scanCallback);

			if (mBluetoothGatt != null && writeCharacteristic != null) {
				if (!mBluetoothGatt.getDevice().getAddress()
						.equals(m_myData.address)) {
					mBluetoothGatt.disconnect();
					mBluetoothGatt.close();
					mBluetoothGatt = null;
					getTime();
					updateInfo.append(openTime+"  ���ڳ�������......\n");
					mBluetoothGatt = m_myData.device.connectGatt(
							MainActivity.this, false, mGattCallbackOAD);
				}
			} else {
				getTime();
				updateInfo.append(openTime+"  ���ڳ�������......\n");
				mBluetoothGatt = m_myData.device.connectGatt(
						MainActivity.this, false, mGattCallbackOAD);
			}

			if (mProgramming) {
				stopProgramming();
			}
	   }
	   
	//��������
	public void restartBle() {
		mBluetoothLeScanner.stopScan(scanCallback);

		mBluetoothAdapter.disable();
		DataCleanManager.cleanApplicationData(MainActivity.this);

		BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

		mBluetoothAdapter = bluetoothManager.getAdapter();

		if (mBluetoothAdapter == null) {
			Toast.makeText(MainActivity.this, "��ȡ����ʧ��", Toast.LENGTH_SHORT)
					.show();
			finish();
		} else {
			if (!mBluetoothAdapter.isEnabled()) {
				Intent enableBtIntent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, 188);
			} else {
				mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();

				bleScanFilters = new ArrayList<>();
				bleScanFilters.add(new ScanFilter.Builder().setServiceUuid(
						findServerUUID).build());

				bleScanSettings = new ScanSettings.Builder().build();

				if (mBluetoothGatt != null) {
					mBluetoothGatt.disconnect();
					mBluetoothGatt.close();
					mBluetoothGatt = null;
				}

				m_myData.device = null;
				m_myData.address = "";
				m_myData.count = 0;
				m_myData.name = "";
				m_myData.version = "";
				info.setText("");

				mBluetoothLeScanner.startScan(null, bleScanSettings,
						scanCallback);
			}

		}
	}
	
	
	private byte[] oldPwd; 
	private byte[] newPwd;
	//�޸�����
	private void modifyPassword(){
		
		LayoutInflater factory = LayoutInflater.from(this);  
        final View textEntryView = factory.inflate(R.layout.password_edit, null);  
        final EditText password1 = (EditText) textEntryView.findViewById(R.id.editTextPwd1);  
        final EditText password2 = (EditText)textEntryView.findViewById(R.id.editTextPwd2);  
        AlertDialog.Builder ad1 = new AlertDialog.Builder(MainActivity.this);  
        ad1.setTitle("�޸�����");  
        ad1.setIcon(android.R.drawable.ic_dialog_info);  
        ad1.setView(textEntryView);  
        ad1.setPositiveButton("�޸�", new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialog, int i) {  

                String pwd1 = password1.getText().toString();
                String pwd2 = password2.getText().toString();
                
                if (pwd1.length()!=6 || pwd2.length()!=6 ||pwd1 ==null||pwd2 == null) {
                	Toast toast = Toast.makeText(
							MainActivity.this,
							"���������ʽ����ȷ��",
							Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0,
							0);
					toast.show();
				}
                
                try {
					oldPwd = pwd1.getBytes("UTF-8");
					newPwd = pwd2.getBytes("UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (m_myData.device == null) {
					Toast toast = Toast.makeText(
							MainActivity.this,
							"û���豸������ɨ��",
							Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0,
							0);
					toast.show();
					return;
				}

				mBluetoothLeScanner
						.stopScan(scanCallback);
				caozuo = CAOZUO_MIMA;

				if (mBluetoothGatt != null
						&& writeCharacteristic != null) {
					if (mBluetoothGatt.getDevice()
							.getAddress()
							.equals(m_myData.address)) {
						byte[] sendMiMa1 = { 0x05,
								0x03, 0x06, mima[0],
								mima[1], mima[2],
								mima[3], mima[4],
								mima[5], token[0],
								token[1], token[2],
								token[3], 0x00, 0x00,
								0x00 };
						
//						byte[] sendMiMa2 = { 0x05,
//								0x04, 0x06, newPwd[0],
//								newPwd[1], newPwd[2],
//								newPwd[3], newPwd[4],
//								newPwd[5], token[0],
//								token[1], token[2],
//								token[3], 0x00, 0x00,
//								0x00 };
//						
						SendData(sendMiMa1);
//						SendData(sendMiMa2);
					} else {
						mBluetoothGatt.disconnect();
						mBluetoothGatt.close();
						mBluetoothGatt = null;

						mBluetoothGatt = m_myData.device
								.connectGatt(
										MainActivity.this,
										false,
										mGattCallback);
					}
				} else {
					mBluetoothGatt = m_myData.device
							.connectGatt(
									MainActivity.this,
									false,
									mGattCallback);
				}

			
            
  
            }  
        });  
        ad1.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialog, int i) {  
  
            }  
        });  
        ad1.show();// ��ʾ�Ի���  
		
//		new AlertDialog.Builder(MainActivity.this)
//		.setTitle("��ȷ��")
//		.setPositiveButton("�޸�",
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog,
//							int which) {}
//				})
//		.setNegativeButton("ȡ��",
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog,
//							int which) {
//						dialog.cancel();
//					}
//				}).show();	
	}
	
	//�޸���Կ�ķ���
	 private void modifyKey() {
		 
		 new AlertDialog.Builder(MainActivity.this)
			.setTitle("��ȷ��")
			.setPositiveButton("�޸�",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int which) {

							if (m_myData.device == null) {
								Toast toast = Toast.makeText(
										MainActivity.this,
										"û���豸������ɨ��",
										Toast.LENGTH_SHORT);
								toast.setGravity(Gravity.CENTER, 0,
										0);
								toast.show();
								return;
							}

							mBluetoothLeScanner
									.stopScan(scanCallback);
							caozuo = CAOZUO_MIYUE;

							if (mBluetoothGatt != null
									&& writeCharacteristic != null) {
								if (mBluetoothGatt.getDevice()
										.getAddress()
										.equals(m_myData.address)) {
									byte[] sendMiYue1 = { 0x07,
											0x01, 0x08, key2[0],
											key2[1], key2[2],
											key2[3], key2[4],
											key2[5], key2[6],
											key2[7], token[0],
											token[1], token[2],
											token[3], 0x00 };
									SendData(sendMiYue1);
								} else {
									mBluetoothGatt.disconnect();
									mBluetoothGatt.close();
									mBluetoothGatt = null;

									mBluetoothGatt = m_myData.device
											.connectGatt(
													MainActivity.this,
													false,
													mGattCallback);
								}
							} else {
								mBluetoothGatt = m_myData.device
										.connectGatt(
												MainActivity.this,
												false,
												mGattCallback);
							}

						}
					})
			.setNegativeButton("ȡ��",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int which) {
							dialog.cancel();
						}
					}).show();
			
		}
	 
	 //��ѯ������״̬����
	 private void searchState() {
		 
		 if (m_myData.device == null) {
				Toast toast = Toast.makeText(MainActivity.this,
						"û���豸������ɨ��", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}

			mBluetoothLeScanner.stopScan(scanCallback);
			caozuo = CAOZUO_ZHUANGTAI;

			if (mBluetoothGatt != null && writeCharacteristic != null) {
				if (mBluetoothGatt.getDevice().getAddress()
						.equals(m_myData.address)) {
					byte[] zhuangTai = { 0x05, 0x0E, 0x01, 0X01, token[0],
							token[1], token[2], token[3], 0x00, 0x00, 0x00,
							0x00, 0x00, 0x00, 0x00, 0x00 };
					SendData(zhuangTai);
				} else {
					mBluetoothGatt.disconnect();
					mBluetoothGatt.close();
					mBluetoothGatt = null;

					mBluetoothGatt = m_myData.device.connectGatt(
							MainActivity.this, false, mGattCallback);
				}
			} else {
				mBluetoothGatt = m_myData.device.connectGatt(
						MainActivity.this, false, mGattCallback);
			}
			
		}
	 
	 //���û���״ָ̬���
	 private void setHuancheState() {
		 
		 if (m_myData.device == null) {
				Toast toast = Toast.makeText(MainActivity.this,
						"û���豸������ɨ��", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}

			mBluetoothLeScanner.stopScan(scanCallback);
			caozuo = CAOZUO_HUANCHESTATE;

			if (mBluetoothGatt != null && writeCharacteristic != null) {
				if (mBluetoothGatt.getDevice().getAddress()
						.equals(m_myData.address)) {
					byte[] setHuanche = { 0x05, 0x14, 0x01, 0X01, token[0],
							token[1], token[2], token[3], 0x00, 0x00, 0x00,
							0x00, 0x00, 0x00, 0x00, 0x00 };
					SendData(setHuanche);
				} else {
					mBluetoothGatt.disconnect();
					mBluetoothGatt.close();
					mBluetoothGatt = null;

					mBluetoothGatt = m_myData.device.connectGatt(
							MainActivity.this, false, mGattCallback);
				}
			} else {
				mBluetoothGatt = m_myData.device.connectGatt(
						MainActivity.this, false, mGattCallback);
			}
			
		}
	 
	 	//��ѯ���Ĺ���ģʽָ��ķ���
		private void searchLockModel() {
			 if (m_myData.device == null) {
					Toast toast = Toast.makeText(MainActivity.this,
							"û���豸������ɨ��", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					return;
				}

				mBluetoothLeScanner.stopScan(scanCallback);
				caozuo = CAOZUO_SEARCHLOCKMODEL;

				if (mBluetoothGatt != null && writeCharacteristic != null) {
					if (mBluetoothGatt.getDevice().getAddress()
							.equals(m_myData.address)) {
						byte[] searchLockModle = { 0x05, 0x20, 0x01, 0X00, token[0],
								token[1], token[2], token[3], 0x00, 0x00, 0x00,
								0x00, 0x00, 0x00, 0x00, 0x00 };
						SendData(searchLockModle);
					} else {
						mBluetoothGatt.disconnect();
						mBluetoothGatt.close();
						mBluetoothGatt = null;

						mBluetoothGatt = m_myData.device.connectGatt(
								MainActivity.this, false, mGattCallback);
					}
				} else {
					mBluetoothGatt = m_myData.device.connectGatt(
							MainActivity.this, false, mGattCallback);
				}
		}
		
		
		

		//��ѯ���Ĺ���״̬����
		private void searchLockWorkState() {
			
			 if (m_myData.device == null) {
					Toast toast = Toast.makeText(MainActivity.this,
							"û���豸������ɨ��", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					return;
				}

				mBluetoothLeScanner.stopScan(scanCallback);
				caozuo = CAOZUO_SEARCHLOCKWORKSTATE;

				if (mBluetoothGatt != null && writeCharacteristic != null) {
					if (mBluetoothGatt.getDevice().getAddress()
							.equals(m_myData.address)) {
						byte[] searchLockWork = { 0x05, 0x22, 0x01, 0X00, token[0],
								token[1], token[2], token[3], 0x00, 0x00, 0x00,
								0x00, 0x00, 0x00, 0x00, 0x00 };
						SendData(searchLockWork);
					} else {
						mBluetoothGatt.disconnect();
						mBluetoothGatt.close();
						mBluetoothGatt = null;

						mBluetoothGatt = m_myData.device.connectGatt(
								MainActivity.this, false, mGattCallback);
					}
				} else {
					mBluetoothGatt = m_myData.device.connectGatt(
							MainActivity.this, false, mGattCallback);
				}
			
			
		}
		
		//������״̬����
	private void KongzhiState() {
					 if (m_myData.device == null) {
							Toast toast = Toast.makeText(MainActivity.this,
									"û���豸������ɨ��", Toast.LENGTH_SHORT);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
							return;
						}

						mBluetoothLeScanner.stopScan(scanCallback);
						caozuo = CAOZUO_KONGZHISTATE;

						if (mBluetoothGatt != null && writeCharacteristic != null) {
							if (mBluetoothGatt.getDevice().getAddress()
									.equals(m_myData.address)) {
								byte[] kongzhiState = { 0x05, 0x50, 0x01, 0X00, token[0],
										token[1], token[2], token[3], 0x00, 0x00, 0x00,
										0x00, 0x00, 0x00, 0x00, 0x00 };
								SendData(kongzhiState);
							} else {
								mBluetoothGatt.disconnect();
								mBluetoothGatt.close();
								mBluetoothGatt = null;

								mBluetoothGatt = m_myData.device.connectGatt(
										MainActivity.this, false, mGattCallback);
							}
						} else {
							mBluetoothGatt = m_myData.device.connectGatt(
									MainActivity.this, false, mGattCallback);
						}
					
					
				}
		
	//��ѯ����GIM_ID����
	private void searchGIM_ID() {
			
		 if (m_myData.device == null) {
				Toast toast = Toast.makeText(MainActivity.this,
						"û���豸������ɨ��", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}

			mBluetoothLeScanner.stopScan(scanCallback);
			caozuo = CAOZUO_GIM_ID;

			if (mBluetoothGatt != null && writeCharacteristic != null) {
				if (mBluetoothGatt.getDevice().getAddress()
						.equals(m_myData.address)) {
					byte[] searchGimId = { 0x05, 0x23, 0x01, 0X00, token[0],
							token[1], token[2], token[3], 0x00, 0x00, 0x00,
							0x00, 0x00, 0x00, 0x00, 0x00 };
					SendData(searchGimId);
				} else {
					mBluetoothGatt.disconnect();
					mBluetoothGatt.close();
					mBluetoothGatt = null;

					mBluetoothGatt = m_myData.device.connectGatt(
							MainActivity.this, false, mGattCallback);
				}
			} else {
				mBluetoothGatt = m_myData.device.connectGatt(
						MainActivity.this, false, mGattCallback);
			}
		
		}
	

	private void searchGSM_Ver() {
		 if (m_myData.device == null) {
				Toast toast = Toast.makeText(MainActivity.this,
						"û���豸������ɨ��", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}

			mBluetoothLeScanner.stopScan(scanCallback);
			caozuo = CAOZUO_GSM_VER;

			if (mBluetoothGatt != null && writeCharacteristic != null) {
				if (mBluetoothGatt.getDevice().getAddress()
						.equals(m_myData.address)) {
					byte[] searchGsmVer = { 0x05, 0x24, 0x01, 0X00, token[0],
							token[1], token[2], token[3], 0x00, 0x00, 0x00,
							0x00, 0x00, 0x00, 0x00, 0x00 };
					SendData(searchGsmVer);
				} else {
					mBluetoothGatt.disconnect();
					mBluetoothGatt.close();
					mBluetoothGatt = null;

					mBluetoothGatt = m_myData.device.connectGatt(
							MainActivity.this, false, mGattCallback);
				}
			} else {
				mBluetoothGatt = m_myData.device.connectGatt(
						MainActivity.this, false, mGattCallback);
			}
	}
		
	 //�¿�����Կ�ķ���
	 private void newKey() {
		 if (m_myData.device == null) {
				Toast toast = Toast.makeText(MainActivity.this,
						"û���豸������ɨ��", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}

			if (!m_xinMiYue) {
				Toast toast = Toast.makeText(MainActivity.this, "ֻ��������Կ����",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}

			mBluetoothLeScanner.stopScan(scanCallback);
			caozuo = CAOZUO_OPEN;

			if (mBluetoothGatt != null && writeCharacteristic != null) {
				if (mBluetoothGatt.getDevice().getAddress()
						.equals(m_myData.address)) {
					byte[] openLock = { 0x05, 0x01, 0x06, mima[0], mima[1],
							mima[2], mima[3], mima[4], mima[5], token[0],
							token[1], token[2], token[3], 0x00, 0x00, 0x00 };
					SendData(openLock);
				} else {
					mBluetoothGatt.disconnect();
					mBluetoothGatt.close();
					mBluetoothGatt = null;

					mBluetoothGatt = m_myData.device.connectGatt(
							MainActivity.this, false, mGattCallback);
				}
			} else {
				mBluetoothGatt = m_myData.device.connectGatt(
						MainActivity.this, false, mGattCallback);
			}
		}
	 
	 //΢����Ȩ����
		private void shouQuan() {
			if (m_myData.device == null) {
				Toast toast = Toast.makeText(MainActivity.this,
						"û���豸������ɨ��", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}

			mBluetoothLeScanner.stopScan(scanCallback);

			m_nowMac = m_myData.address;
			// ��ɨ�����ɨ����������ά��
			Intent openCameraIntent = new Intent(MainActivity.this,
					CaptureActivity.class);
			startActivityForResult(openCameraIntent, 688);
		}
		
		//��ȡ��Կ
		private void getKey() {
			mBluetoothLeScanner.stopScan(scanCallback);
			// ��ɨ�����ɨ����������ά��
			Intent openCameraIntent = new Intent(MainActivity.this,
					CaptureActivity.class);
			startActivityForResult(openCameraIntent, 788);
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings1) {

			m_xinMiYue = false;

			if (mBluetoothGatt != null) {
				mBluetoothGatt.disconnect();
				mBluetoothGatt.close();
				mBluetoothGatt = null;
			}

			m_myData.device = null;
			m_myData.address = "";
			m_myData.count = 0;
			m_myData.name = "";
			m_myData.version = "";
			info.setText("");

			mBluetoothLeScanner.startScan(bleScanFilters, bleScanSettings,
					scanCallback);

		} else if (id == R.id.action_settings2) {
			mBluetoothLeScanner.stopScan(scanCallback);
		}

		return super.onOptionsItemSelected(item);
	}

	public void SendData(byte[] data) {
		if (m_xinMiYue) {
			byte miwen[] = Encrypt(data, key2);
			if (miwen != null) {
				writeCharacteristic.setValue(miwen);
				mBluetoothGatt.writeCharacteristic(writeCharacteristic);
			}
		} else {
			byte miwen[] = Encrypt(data, key);
			if (miwen != null) {
				writeCharacteristic.setValue(miwen);
				mBluetoothGatt.writeCharacteristic(writeCharacteristic);
			}
		}
		getTime();
	}

	// ����
	public byte[] Encrypt(byte[] sSrc, byte[] sKey) {

		try {
			SecretKeySpec skeySpec = new SecretKeySpec(sKey, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");// "�㷨/ģʽ/���뷽ʽ"
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(sSrc);

			return encrypted;// �˴�ʹ��BASE64��ת�빦�ܣ�ͬʱ����2�μ��ܵ����á�
		} catch (Exception ex) {
			return null;
		}
	}

	// ����
	public byte[] Decrypt(byte[] sSrc, byte[] sKey) {

		try {
			SecretKeySpec skeySpec = new SecretKeySpec(sKey, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] dncrypted = cipher.doFinal(sSrc);
			return dncrypted;

		} catch (Exception ex) {
			return null;
		}
	}

	
	private void getTime(){
		
		SimpleDateFormat sDateFormat =new SimpleDateFormat("HH:mm:ss");
		Date data = new Date(System.currentTimeMillis());
		openTime =sDateFormat.format(data);
		
	}
	
	Handler m_myHandler = new Handler(new Callback() {
		
		@Override
		public boolean handleMessage(Message mes) {
		
			StringBuffer sb = new StringBuffer();
			
			switch (mes.what) {
			case 1: 
				// �����ɹ�
//				updateInfo.append(openTime+mes.obj+"\r\n");
				sb.append(openTime+" ��������");
				sb.append((String)mes.obj+"\r\n");
				updateInfo.setText(sb.toString());
				break;
			
			case 2: 
				// ����ʾ��Ϣ
				Toast toast = Toast.makeText(MainActivity.this,
						(String) mes.obj, Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				updateInfo.append(openTime+mes.obj+"\r\n");
				break;
			
			case 3: 
				info.setText(m_myData.name + "==="
						+ String.valueOf(m_myData.rssi) + "==="
						+ String.valueOf(m_myData.count) + "==="
						+ m_myData.version + "\r\n" + m_myData.address);
				break;
			
			case 4: 
				info.setText((String) mes.obj);
				break;
		
			case 5: 
				String result = (String) mes.obj;
				if (result.equals("0")) {
					updateInfo.append("֧������\n");
					mBluetoothGatt = null;
					restartBle();
				} else {
					updateInfo.append("��֧��������������\n");
				}

				break;
		
			case 6: 
				if (!mProgramming) {
					mProgramming = true;
					startProgramming();
				}
				break;
			
			case 7: 
				updateInfo.append(openTime+(String) mes.obj + "\n");
				break;
			
			case 8: 
				mProgressBar.setProgress((mProgInfo.iBlocks * 100)
						/ mProgInfo.nBlocks);
				proInfo.setText(String.valueOf(mProgInfo.iBlocks) + "/"
						+ String.valueOf(mProgInfo.nBlocks));
				break;
			
			
			case 9: 
				
				updateInfo.append(openTime+"  ���ɹ�!  "+"�����ǣ�" +(String) mes.obj +"%\r\n");
				break;
			
			
			case 10: 
				
				updateInfo.append(openTime+mes.obj+"\r\n");
				break;
			
			
			case 11: //��λ��ص�
				
				sb.append(openTime+" �ص�����");
				sb.append((String)mes.obj+"\r\n");
				updateInfo2.setText(sb.toString());
//				updateInfo.append(openTime+mes.obj+"\r\n");
				break;
			
			
			case 12: 
				
//				updateInfo.append(openTime+mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj);
				updateInfo16.setText(sb.toString());
				break;
			
			case 13: 
				
				updateInfo.append(openTime+mes.obj+"\r\n");
				break;
			
			
			case 14: 
				
				updateInfo.append(openTime+mes.obj+"\r\n");
				break;
			
			case 15: 
				
//				updateInfo.append(openTime+mes.obj+"\r\n");
//				sb.append(openTime+" ���״̬��");
//				sb.append((String)mes.obj+"\r\n");
//				updateInfo3.setText(sb.toString());
				sb.append(openTime+" ���״̬: "+(String)mes.obj);
				updateInfo18.setText(sb.toString());
				break;
			
			
			case 16: 
				
				updateInfo.append(openTime+mes.obj+"\r\n");
				break;
			
			case 17: 
				
//				updateInfo5.append(openTime+mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj);
				updateInfo5.setText(sb.toString());
				break;
			
			
			case 18: 
				
//				updateInfo5.append(openTime+mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj);
				updateInfo6.setText(sb.toString());
				break;
			
			case 19: 
				
//				updateInfo5.append(openTime+mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj);
				updateInfo7.setText(sb.toString());
				break;
			
			
			case 20: 
				
//				updateInfo5.append(openTime+mes.obj+"\r\n");
//				sb.append(openTime+" "+(String)mes.obj+"\r\n");
//				updateInfo8.setText(sb.toString());
				break;
			case 21: 
				
//				updateInfo5.append(openTime+"  ��ǰ����: "+(String)mes.obj+"%\r\n");
//				sb.append(openTime+"  ��ǰ����: "+" "+(String)mes.obj+"\r\n");
//				updateInfo9.setText(sb.toString());
				break;
			
			case 22: 
				
//				updateInfo5.append(openTime+(String)mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj);
				updateInfo10.setText(sb.toString());
				break;
			
			case 23: 
				
//				updateInfo5.append(openTime+(String)mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj);
				updateInfo11.setText(sb.toString());
				break;
			
			case 24: 
				
//				updateInfo5.append(openTime+(String)mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj);
				updateInfo12.setText(sb.toString());
				break;
			
			case 25: 
				
//				updateInfo5.append(openTime+(String)mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj);
				updateInfo13.setText(sb.toString());
				break;
			
			
			case 26: 
				
//				updateInfo5.append(openTime+(String)mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj);
				updateInfo14.setText(sb.toString());
				break;

			case 27: 
	
//				updateInfo5.append(openTime+(String)mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj);
				updateInfo15.setText(sb.toString());
				break;

			case 28: 
	
				sb.append(openTime+" GSM�汾����: "+(String)mes.obj);
				updateInfo1.setText(sb.toString().trim()+"\r\n");
//				updateInfo.append("  GSM�汾���ǣ�");
//				updateInfo.append(openTime+(String)mes.obj+"\r\n");
				break;
			
			case 288: 
				
				sb.append(openTime+" "+(String)mes.obj+"\r\n");
				updateInfo17.setText(sb.toString().trim());
//				updateInfo.append("  GSM�汾���ǣ�");
//				updateInfo.append(openTime+(String)mes.obj+"\r\n");
				break;
				
			case 29: 
				
				updateInfo.append(openTime+(String)mes.obj+"\r\n");
				break;
			
			case 30:
				
				updateInfo.append(openTime+(String)mes.obj+"\r\n");
				break;
				
			case 31:
				
				updateInfo.append(openTime+(String)mes.obj+"\r\n");
				break;
			case 41: 
				
//				updateInfo19.append(openTime+(String)mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj+"\r\n");
				updateInfo20.setText(sb.toString().trim());
				break;
			
			case 42: 
				
//				updateInfo.append(openTime+(String)mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj+"\r\n");
				updateInfo21.setText(sb.toString().trim());
				break;
			
			case 43: 
				
//				updateInfo.append(openTime+(String)mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj+"\r\n");
				updateInfo22.setText(sb.toString().trim());
				break;
			
			case 44: 
				
//				updateInfo.append(openTime+(String)mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj+"\r\n");
				updateInfo23.setText(sb.toString().trim());
				break;
			
			
			case 45: 
				
//				updateInfo.append(openTime+(String)mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj+"\r\n");
				updateInfo24.setText(sb.toString().trim());
				break;

			case 46: 
	
//				updateInfo.append(openTime+(String)mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj+"\r\n");
				updateInfo25.setText(sb.toString().trim());
				break;

			case 47: 
	
//				updateInfo.append(openTime+(String)mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj+"\r\n");
				updateInfo26.setText(sb.toString().trim());
				break;
			
			case 48: 
				
//				updateInfo.append(openTime+(String)mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj+"\r\n");
				updateInfo27.setText(sb.toString().trim());
				break;
			
			case 49:
				
//				updateInfo.append(openTime+(String)mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj+"\r\n");
				updateInfo28.setText(sb.toString().trim());
				break;
				
			case 50:
				
//				updateInfo.append(openTime+(String)mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj+"\r\n");
				updateInfo29.setText(sb.toString().trim());
				break;
			case 51:
				
//				updateInfo.append(openTime+(String)mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj+"\r\n");
				updateInfo30.setText(sb.toString().trim());
				break;
				
			case 52:
				
//				updateInfo.append(openTime+(String)mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj+"\r\n");
				updateInfo31.setText(sb.toString().trim());
				break;
				
			case 53:
				
//				updateInfo.append(openTime+(String)mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj+"\r\n");
				updateInfo32.setText(sb.toString().trim());
				break;
				
			case 54:
				
//				updateInfo.append(openTime+(String)mes.obj+"\r\n");
				sb.append(openTime+" "+(String)mes.obj+"\r\n");
				updateInfo33.setText(sb.toString().trim());
				break;
				
			case 55:
				
				updateInfo.append(openTime+(String)mes.obj+"\r\n");
				break;
				
			case 56:
				
				updateInfo.append(openTime+(String)mes.obj+"\r\n");
				break;
			default:
				break;
			}
			
//			updateInfo.setText(sb.toString());
			return false;
		}
	});

	private ScanCallback scanCallback = new ScanCallback() {

		public void onScanResult(int callbackType, ScanResult result) {
			BluetoothDevice device = result.getDevice();
			int rssi = result.getRssi();
			byte[] data = result.getScanRecord().getBytes();
			if (data[5] == 0x01 && data[6] == 0x02) {
				String nowAddress = device.getAddress();
				if (nowAddress == m_myData.address) {
					if (m_myData.rssi != rssi) {
						m_myData.rssi = rssi;
						m_myHandler.sendEmptyMessage(3);
					}
				} else if (rssi > m_myData.rssi || m_myData.device == null) {
					m_myData.device = device;
					m_myData.name = device.getName();
					m_myData.address = nowAddress;
					m_myData.rssi = rssi;
					m_myData.count = 0;
					m_myData.version = "";
					m_myHandler.sendEmptyMessage(3);
				}
			}
		}
	};
	
	private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
		@Override
		public void onConnectionStateChange(BluetoothGatt gatt, int status,
				int newState) {
			if (newState == BluetoothProfile.STATE_CONNECTED) {
				gatt.discoverServices();

			} else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
				mBluetoothGatt.disconnect();
				mBluetoothGatt.close();
				mBluetoothGatt = null;
			}
		}

		@Override
		public void onServicesDiscovered(BluetoothGatt gatt, int status) {
			Log.w("TAG", "onServicesDiscovered");

			if (status == BluetoothGatt.GATT_SUCCESS) {
				BluetoothGattService service = gatt.getService(bltServerUUID);
				readCharacteristic = service.getCharacteristic(readDataUUID);
				writeCharacteristic = service.getCharacteristic(writeDataUUID);

				gatt.setCharacteristicNotification(readCharacteristic, true);

				BluetoothGattDescriptor descriptor = readCharacteristic
						.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG);
				descriptor
						.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
				gatt.writeDescriptor(descriptor);

			}
		}

		@Override
		public void onDescriptorWrite(BluetoothGatt gatt,
				BluetoothGattDescriptor descriptor, int status) {
			SendData(gettoken);
		}

		private Message msg;
		@Override
		public void onCharacteristicChanged(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic) {

			byte[] values = characteristic.getValue();
			byte[] x = new byte[16];
			System.arraycopy(values, 0, x, 0, 16);
			final byte[] mingwen;
			if (m_xinMiYue) {
				mingwen = Decrypt(x, key2);
			} else {
				mingwen = Decrypt(x, key);
			}

			if (mingwen != null && mingwen.length == 16) {
				if (mingwen[0] == 0x06 && mingwen[1] == 0x02) {
					token[0] = mingwen[3];
					token[1] = mingwen[4];
					token[2] = mingwen[5];
					token[3] = mingwen[6];
					buttonGetToken.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							
							updateInfo.append(openTime +"  ���ƣ�"+String.valueOf(mingwen[3])+String.valueOf(mingwen[4])+String.valueOf(mingwen[5])+String.valueOf(mingwen[6])
									+"\nоƬƽ̨���ͣ�"+String.valueOf(mingwen[7])
									+"\n�̼��汾�ţ�"+String.valueOf(mingwen[8])
									+"\n�豸���ͣ�"+String.valueOf(mingwen[9])+"\n\r");
							
						}
					});
					m_myData.version = String.valueOf(mingwen[8]) + "."
							+ String.valueOf(mingwen[9]);
					m_myHandler.sendEmptyMessage(3);
					
					if (caozuo == CAOZUO_OPEN) {
						byte[] openLock = { 0x05, 0x01, 0x06, mima[0], mima[1],
								mima[2], mima[3], mima[4], mima[5], token[0],
								token[1], token[2], token[3], 0x00, 0x00, 0x00 };
						SendData(openLock);
					} else if (caozuo == CAOZUO_CLOSE) {
						byte[] closeLock = { 0x05, 0x0c, 0x01, 0x01, token[0],
								token[1], token[2], token[3], 0x00, 0x00, 0x00,
								0x00, 0x00, 0x00, 0x00, 0x00 };
						SendData(closeLock);
					} else if (caozuo == CAOZUO_DIANLIANG) {
						byte[] dianLiang = { 0x02, 0x01, 0x01, 0x01, token[0],
								token[1], token[2], token[3], 0x00, 0x00, 0x00,
								0x00, 0x00, 0x00, 0x00, 0x00 };
						SendData(dianLiang);
					} else if (caozuo == CAOZUO_OAD) {
						byte[] oad = { 0x03, 0x01, 0x01, 0x01, token[0],
								token[1], token[2], token[3], 0x00, 0x00, 0x00,
								0x00, 0x00, 0x00, 0x00, 0x00 };
						SendData(oad);
					} else if (caozuo == CAOZUO_MIMA) {
						byte[] sendMiMa1 = { 0x05, 0x03, 0x06, mima[0],
								mima[1], mima[2], mima[3], mima[4], mima[5],
								token[0], token[1], token[2], token[3], 0x00,
								0x00, 0x00 };
//						byte[] sendMiMa2 = { 0x05, 0x03, 0x06, mima[0],
//								mima[1], mima[2], mima[3], mima[4], mima[5],
//								token[0], token[1], token[2], token[3], 0x00,
//								0x00, 0x00 };
						SendData(sendMiMa1);
//						SendData(sendMiMa2);
					} else if (caozuo == CAOZUO_MIYUE) {
						byte[] sendMiYue1 = { 0x07, 0x01, 0x08, key2[0],
								key2[1], key2[2], key2[3], key2[4], key2[5],
								key2[6], key2[7], token[0], token[1], token[2],
								token[3], 0x00 };
						SendData(sendMiYue1);
					} else if (caozuo == CAOZUO_ZHUANGTAI) {
						byte[] zhuangTai = { 0x05, 0x0E, 0x01, 0X01, token[0],
								token[1], token[2], token[3], 0x00, 0x00, 0x00,
								0x00, 0x00, 0x00, 0x00, 0x00 };
						SendData(zhuangTai);
					}else if (caozuo == CAOZUO_HUANCHESTATE) {
						byte[] setHuanche = { 0x05, 0x14, 0x01, 0X01, token[0],
								token[1], token[2], token[3], 0x00, 0x00, 0x00,
								0x00, 0x00, 0x00, 0x00, 0x00 };
						SendData(setHuanche);
					}else if (caozuo == CAOZUO_SEARCHLOCKMODEL) {
						byte[] searchLockModle = { 0x05, 0x20, 0x01, 0X00, token[0],
								token[1], token[2], token[3], 0x00, 0x00, 0x00,
								0x00, 0x00, 0x00, 0x00, 0x00 };
						SendData(searchLockModle);
					}else if (caozuo == CAOZUO_GIM_ID) {
						byte[] searchGsmVer = { 0x05, 0x23, 0x01, 0X00, token[0],
								token[1], token[2], token[3], 0x00, 0x00, 0x00,
								0x00, 0x00, 0x00, 0x00, 0x00 };
						SendData(searchGsmVer);
					}else if (caozuo == CAOZUO_GSM_VER) {
						byte[] searchGimId = { 0x05, 0x24, 0x01, 0X00, token[0],
								token[1], token[2], token[3], 0x00, 0x00, 0x00,
								0x00, 0x00, 0x00, 0x00, 0x00 };
						SendData(searchGimId);
					}else if (caozuo == CAOZUO_KONGZHISTATE) {
						byte[] kongzhiState = { 0x05, 0x50, 0x01, 0X00, token[0],
								token[1], token[2], token[3], 0x00, 0x00, 0x00,
								0x00, 0x00, 0x00, 0x00, 0x00 };
						SendData(kongzhiState);
					}else if (caozuo == CAOZUO_SETLOCKMODEL) {
						byte[] zhengchangModle = { 0x05, 0x21, 0x01, 0X00, token[0],
								token[1], token[2], token[3], 0x00, 0x00, 0x00,
								0x00, 0x00, 0x00, 0x00, 0x00 };
						
						byte[] yunshuModle = { 0x05, 0x21, 0x01, 0X01, token[0],
								token[1], token[2], token[3], 0x00, 0x00, 0x00,
								0x00, 0x00, 0x00, 0x00, 0x00 };
						
						byte[] chongqiModle = { 0x05, 0x21, 0x01, 0X02, token[0],
								token[1], token[2], token[3], 0x00, 0x00, 0x00,
								0x00, 0x00, 0x00, 0x00, 0x00 };
						SendData(zhengchangModle);
						SendData(yunshuModle);
						SendData(chongqiModle);
					}else if (caozuo == CAOZUO_SEARCHLOCKWORKSTATE) {
						byte[] searchLockWork = { 0x05, 0x22, 0x01, 0X00, token[0],
								token[1], token[2], token[3], 0x00, 0x00, 0x00,
								0x00, 0x00, 0x00, 0x00, 0x00 };
						SendData(searchLockWork);
						}
					
				} else if (mingwen[0] == 0x05 && mingwen[1] == 0x02) {
					if (mingwen[3] == 0x00) {
						Message msg = m_myHandler.obtainMessage(1, 1, 1,"  ����ɹ���");
						m_myHandler.sendMessage(msg);
					} else {
						Message msg = m_myHandler.obtainMessage(1, 1, 1, "  ����ʧ�ܣ�");
						m_myHandler.sendMessage(msg);
					}
				} else if (mingwen[0] == 0x02 && mingwen[1] == 0x02) {
					if (mingwen[3] == 0xff) {
						Message msg = m_myHandler.obtainMessage(9, 1, 1,
								"������ʧ��");
						m_myHandler.sendMessage(msg);
					} else {
						Message msg = m_myHandler.obtainMessage(9, 1, 1,
								String.valueOf(mingwen[3]));
						m_myHandler.sendMessage(msg);
					}
				} else if (mingwen[0] == 0x03 && mingwen[1] == 0x02) {
					Message msg = m_myHandler.obtainMessage(5, 1, 1,
							String.valueOf(mingwen[3]));
					m_myHandler.sendMessage(msg);
				} else if (mingwen[0] == 0x05 && mingwen[1] == 0x05) {
					if (mingwen[3] == 0x00) {
						Message msg = m_myHandler.obtainMessage(10, 1, 1,
								"  �޸�����ɹ���");
						m_myHandler.sendMessage(msg);
					} else {
						Message msg = m_myHandler.obtainMessage(10, 1, 1,
								"  �޸�����ʧ�ܣ�");
						m_myHandler.sendMessage(msg);
					}
				} else if (mingwen[0] == 0x05 && mingwen[1] == 0x08) {
					if (mingwen[3] == 0x00) {
						Message msg = m_myHandler
								.obtainMessage(2, 1, 1, "  �ص�ɹ���");
						m_myHandler.sendMessage(msg);
					} else {
						Message msg = m_myHandler
								.obtainMessage(2, 1, 1, "  �ص�ʧ�ܣ�");
						m_myHandler.sendMessage(msg);
					}
				} else if (mingwen[0] == 0x05 && mingwen[1] == 0x0d) {
					if (mingwen[3] == 0x00) {
						Message msg = m_myHandler
								.obtainMessage(11, 1, 1, "  �ص�ɹ���");
						m_myHandler.sendMessage(msg);
					} else {
						Message msg = m_myHandler
								.obtainMessage(11, 1, 1, "  �ص�ʧ�ܣ�");
						m_myHandler.sendMessage(msg);
					}
				} else if (mingwen[0] == 0x05 && mingwen[1] == 0x0f) {
					if (mingwen[3] == 0x00) {
						Message msg = m_myHandler
								.obtainMessage(12, 1, 1, " ��Դ����״̬: ����״̬��");
						m_myHandler.sendMessage(msg);
					} else if(mingwen[3] == 0x01) {
						Message msg = m_myHandler
								.obtainMessage(12, 1, 1, "  ��Դ����״̬: �ر�״̬��");
						m_myHandler.sendMessage(msg);
					}
				}else if (mingwen[0] == 0x05 && mingwen[1] == 0x15) {
					
					if (mingwen[3] == 0x00) {
						Message msg = m_myHandler
								.obtainMessage(13, 1, 1, "  �������óɹ���");
						m_myHandler.sendMessage(msg);
					} else if(mingwen[3] == 0x01) {
						Message msg = m_myHandler
								.obtainMessage(13, 1, 1, "  ��������ʧ�ܣ�");
						m_myHandler.sendMessage(msg);
					}
					
				}else if (mingwen[0] == 0x05 && mingwen[1] == 0x20) {
					if (mingwen[3] == 0x00) {
						Message msg = m_myHandler
								.obtainMessage(15, 1, 1, "  ��ǰΪ����״̬��");
						m_myHandler.sendMessage(msg);
					} else if(mingwen[3] == 0x01) {
						Message msg = m_myHandler
								.obtainMessage(15, 1, 1, "  ��ǰΪ���״̬��");
						m_myHandler.sendMessage(msg);
					}
				} else if (mingwen[0] == 0x05 && mingwen[1] == 0x21) {
					if (mingwen[3] == 0x00) {
						Message msg = m_myHandler
								.obtainMessage(40, 1, 1, "  ��ģʽ���óɹ���");
						m_myHandler.sendMessage(msg);
					} else if(mingwen[3] == 0x01) {
						Message msg = m_myHandler
								.obtainMessage(40, 1, 1, "  ��ģʽ����ʧ�ܣ�");
						m_myHandler.sendMessage(msg);
					}
				}else if (mingwen[0] == 0x05 && mingwen[1] == 0x22) {
					if (mingwen[2] == 0x08) {
						
						byte R1 = mingwen[3];
						byte R2 = mingwen[4];
						byte R3 = mingwen[5];
						Log.e("tag===========", R3+"");
						byte R4 = mingwen[6];
						byte R5 = mingwen[7];
						byte R6 = mingwen[8];
						byte R7 = mingwen[9];
						byte R8 = mingwen[10];
						
						byte R1_Byte0 =(byte) (R1 & 1);
						byte R1_Byte1 =(byte) (R1 & 1<<1);
						byte R1_Byte2 =(byte) (R1 & 1<<2);
						byte R1_Byte3 =(byte) (R1 & 1<<3);
						
						if (R1_Byte0==1) {
							Message msg = m_myHandler
									.obtainMessage(17, 1, 1, "  ��Դ״̬����");
							m_myHandler.sendMessage(msg);
						}else if (R1_Byte0 == 0) {
							Message msg = m_myHandler
									.obtainMessage(17, 1, 1, "  ��Դ״̬����");
							m_myHandler.sendMessage(msg);
						}
						
						if (R1_Byte1 == 2) {
							Message msg = m_myHandler
									.obtainMessage(180, 1, 1, "  �񶯹��ܣ�����");
							m_myHandler.sendMessage(msg);
						}else if (R1_Byte1 == 0) {
							Message msg = m_myHandler
									.obtainMessage(180, 1, 1, "  �񶯹��ܣ�����");
							m_myHandler.sendMessage(msg);
						}
						
						if (R1_Byte2 == 4) {
							Message msg = m_myHandler
									.obtainMessage(19, 1, 1, "  ��״̬����");
							m_myHandler.sendMessage(msg);
						}else if (R1_Byte2 == 0) {
							Message msg = m_myHandler
									.obtainMessage(19, 1, 1, "  ��״̬����ֹ");
							m_myHandler.sendMessage(msg);
						}
						
						if (R1_Byte3 == 8) {
							Message msg = m_myHandler
									.obtainMessage(200, 1, 1, "  ��ŵ�״̬�����");
							m_myHandler.sendMessage(msg);
						}else if (R1_Byte3 == 0) {
							Message msg = m_myHandler
									.obtainMessage(200, 1, 1, "  ��ŵ�״̬���ŵ�");
							m_myHandler.sendMessage(msg);
						}
						
							msg = m_myHandler
								.obtainMessage(210, 1, 1, String.valueOf(R2));
						m_myHandler.sendMessage(msg);
						
						if (R3 ==0) {
							msg = m_myHandler
									.obtainMessage(22, 1, 1, "  GSM״̬���ص�״̬");
							m_myHandler.sendMessage(msg);
						}
						if(R3 ==1){
							msg = m_myHandler
									.obtainMessage(22, 1, 1, "  GSM״̬����ѯSIM��");
							m_myHandler.sendMessage(msg);
						}
						if(R3 ==2){
							msg = m_myHandler
									.obtainMessage(22, 1, 1, "  GSM״̬��ע������");
							m_myHandler.sendMessage(msg);
						}
						if(R3 ==3){
							msg = m_myHandler
									.obtainMessage(22, 1, 1, "  GSM״̬����ʼ�����Ź���");
							m_myHandler.sendMessage(msg);
						}
						if(R3 ==4){
							msg = m_myHandler
									.obtainMessage(22, 1, 1, "  GSM״̬����ѯGPRS����");
							m_myHandler.sendMessage(msg);
						}
						if(R3 ==5){
							msg = m_myHandler
									.obtainMessage(22, 1, 1, "  GSM״̬������PPP");
							m_myHandler.sendMessage(msg);
						}
						if(R3 ==6){
							msg = m_myHandler
									.obtainMessage(22, 1, 1, "  GSM״̬������TCP");
							m_myHandler.sendMessage(msg);
						}
						if(R3 ==7){
							msg = m_myHandler
									.obtainMessage(22, 1, 1, "  GSM״̬����ƽ̨ͨѶ�ɹ�");
							m_myHandler.sendMessage(msg);
						}
						if(R3 ==(byte)0xFE){
							msg = m_myHandler
									.obtainMessage(22, 1, 1, "  GSM״̬�����ϵ�");
							m_myHandler.sendMessage(msg);
						}
						if(R3 ==(byte)0xFF){
							msg = m_myHandler
									.obtainMessage(22, 1, 1, "  GSM״̬������");
							m_myHandler.sendMessage(msg);
						}
						
						msg = m_myHandler
								.obtainMessage(23, 1, 1, "  ��һ��GPRS����ʱ�䣺"+String.valueOf(R4));
						m_myHandler.sendMessage(msg);
						
						msg = m_myHandler
								.obtainMessage(24, 1, 1, "  ��һ��GSM�ź�ǿ��ֵ��"+String.valueOf(R5));
						m_myHandler.sendMessage(msg);
						
						if (R6 ==0) {
							msg = m_myHandler
									.obtainMessage(25, 1, 1, "  GPS״̬���ر�״̬");
							m_myHandler.sendMessage(msg);
						}else if(R6 ==1) {
							msg = m_myHandler
									.obtainMessage(25, 1, 1, "  GPS״̬��δ��λ");
							m_myHandler.sendMessage(msg);
							
						}else if(R6 ==2) {
							msg = m_myHandler
									.obtainMessage(25, 1, 1, "  GPS״̬���Ѷ�λ");
							m_myHandler.sendMessage(msg);
							
						}else if(R6 ==(byte)0xFF) {
							msg = m_myHandler
									.obtainMessage(25, 1, 1, "  GPS״̬������");
							m_myHandler.sendMessage(msg);
						}
						
						msg = m_myHandler
								.obtainMessage(26, 1, 1, "  ��һ��GPS��λʱ�䣺"+String.valueOf(R7));
						m_myHandler.sendMessage(msg);
						
						msg = m_myHandler
								.obtainMessage(27, 1, 1, "  ��һ��GPS����������"+String.valueOf(R8));
						m_myHandler.sendMessage(msg);
						
					}
				}else if (mingwen[0] == 0x05 && mingwen[1] == 0x08) {
					if (mingwen[3] == 0x00) {
						msg = m_myHandler.obtainMessage(29, 1, 1,"  �����ɹ���");
						m_myHandler.sendMessage(msg);
					}else if (mingwen[3] == 0x01) {
						msg = m_myHandler.obtainMessage(29, 1, 1,"  ����ʧ�ܣ�");
						m_myHandler.sendMessage(msg);
					}else if (mingwen[3] == 0x02) {
						msg = m_myHandler.obtainMessage(29, 1, 1,"  �����쳣��");
						m_myHandler.sendMessage(msg);
					}
				}else if (mingwen[0] == 0x03 && mingwen[1] == 0x02) {
					if (mingwen[3] == 0x00) {
						Message msg = m_myHandler
								.obtainMessage(30, 1, 1, "  �̼�������Ӧ����ʼ������");
						m_myHandler.sendMessage(msg);
					} else if(mingwen[3] == 0x01) {
						Message msg = m_myHandler
								.obtainMessage(30, 1, 1, "  �̼�������Ӧ����֧��������");
						m_myHandler.sendMessage(msg);
					}
				}else if (mingwen[0] == 0x05 && mingwen[1] == 0x23) {
					
						msg = m_myHandler
								.obtainMessage(288, 1, 1, "  GSM ID��"
						+String.valueOf(mingwen[2])
						+String.valueOf(mingwen[3])
						+String.valueOf(mingwen[4])
						+String.valueOf(mingwen[5])
						+String.valueOf(mingwen[6])
						+String.valueOf(mingwen[7]));
						m_myHandler.sendMessage(msg);
						
				}else if (mingwen[0] == 0x05 && mingwen[1] == 0x50) {
					
					byte[] nn = new byte[13];
					
//					for (int i = 0; i < nn.length; i++) {
//						nn[i] = mingwen[3+i];
//						
//						Log.e("ggggggggggggg", nn[i]+"");
//					}
					
					nn[0] = mingwen[3];
					nn[1] = mingwen[4];
					nn[2] = mingwen[5];
					nn[3] = mingwen[6];
					nn[4] = mingwen[7];
					nn[5] = mingwen[8];
					nn[6] = mingwen[9];
					nn[7] = mingwen[10];
					nn[8] = mingwen[11];
					nn[9] = mingwen[12];
					nn[10] = mingwen[13];
					nn[11] = mingwen[14];
					nn[12] = mingwen[15];
					
					int N0 = (nn[0] << 8 | nn[1]);
//					Log.e("nnnnnnnn",Integer.toHexString(nn[0]) );
					
					int N1 = (nn[2] << 8 | nn[3]);
					
					int N2 = (nn[4] << 8 | nn[5]);
					
					byte N3 = nn[6];
					
					Log.e("nnnnnnnnnnnnnnn6", Integer.toHexString(N3));
					
					byte N3_Byte0 =(byte) (N3 & 1);
					byte N3_Byte1 =(byte) (N3 & 1<<1);
					byte N3_Byte2 =(byte) (N3 & 1<<2);
					byte N3_Byte3 =(byte) (N3 & 1<<3);
					byte N3_Byte4 =(byte) (N3 & 1<<4);
					byte N3_Byte5 =(byte) (N3 & 1<<5);
					byte N3_Byte6 =(byte) (N3 & 1<<6);
					byte N3_Byte7 =(byte) (N3 & 1<<7);
					
					int N4 = (nn[7] << 8 | nn[8]);
					
					int N5 = (nn[9] << 8 | nn[10]);
					
					int N6 = (nn[11] << 8 | nn[12]);
				
					if(N3_Byte0 == 1){
						Message msg = m_myHandler
								.obtainMessage(41, 1, 1, "  ɲ��״̬��ɲ����Ч");
						m_myHandler.sendMessage(msg);
					}else if (N3_Byte0 == 0) {
						Message msg = m_myHandler
								.obtainMessage(41, 1, 1, "  ɲ��״̬��ɲ����Ч");
						m_myHandler.sendMessage(msg);
					}
					
					if(N3_Byte1 == 2){
						Message msg = m_myHandler
								.obtainMessage(42, 1, 1, "  �ְ�״̬���ְ�����");
						m_myHandler.sendMessage(msg);
					}else if (N3_Byte1 == 0) {
						Message msg = m_myHandler
								.obtainMessage(42, 1, 1, "  �ְ�״̬���ְѲ�����");
						m_myHandler.sendMessage(msg);
					}
					
					if(N3_Byte2 == 4){
						Message msg = m_myHandler
								.obtainMessage(43, 1, 1, "  ����״̬����������");
						m_myHandler.sendMessage(msg);
					}else if (N3_Byte2 == 0) {
						Message msg = m_myHandler
								.obtainMessage(43, 1, 1, "  ����״̬������������");
						m_myHandler.sendMessage(msg);
					}
					
					if(N3_Byte3 == 8){
						Message msg = m_myHandler
								.obtainMessage(44, 1, 1, "  ���״̬����ر���");
						m_myHandler.sendMessage(msg);
					}else if (N3_Byte3 == 0) {
						Message msg = m_myHandler
								.obtainMessage(44, 1, 1, "  ���״̬������ޱ���");
						m_myHandler.sendMessage(msg);
					}
					
					if(N3_Byte4 == 16){
						Message msg = m_myHandler
								.obtainMessage(45, 1, 1, "  �������ϣ���������");
						m_myHandler.sendMessage(msg);
					}else if (N3_Byte4 == 0) {
						Message msg = m_myHandler
								.obtainMessage(45, 1, 1, "  �������ϣ������޹���");
						m_myHandler.sendMessage(msg);
					}
					
					if(N3_Byte5 == 32){
						Message msg = m_myHandler
								.obtainMessage(46, 1, 1, "  ת�ѹ��ϣ�ת�ѹ���");
						m_myHandler.sendMessage(msg);
					}else if (N3_Byte5 == 0) {
						Message msg = m_myHandler
								.obtainMessage(46, 1, 1, "  ת�ѹ��ϣ�ת���޹���");
						m_myHandler.sendMessage(msg);
					}
					
					if(N3_Byte6 == 64){
						Message msg = m_myHandler
								.obtainMessage(47, 1, 1, "  ������ϣ��������");
						m_myHandler.sendMessage(msg);
					}else if (N3_Byte6 == 0) {
						Message msg = m_myHandler
								.obtainMessage(47, 1, 1, "  ������ϣ�����޹���");
						m_myHandler.sendMessage(msg);
					}
					
					if(N3_Byte7 == (byte)0x80){
						Message msg = m_myHandler
								.obtainMessage(48, 1, 1, "  ���״̬���������");
						m_myHandler.sendMessage(msg);
					}else if (N3_Byte7 == 0) {
						Message msg = m_myHandler
								.obtainMessage(48, 1, 1, "  ���״̬�����������");
						m_myHandler.sendMessage(msg);
					}
					
					msg = m_myHandler
							.obtainMessage(49, 1, 1, "  ����̣�"+N0+" km");
					m_myHandler.sendMessage(msg);
					
					msg = m_myHandler
							.obtainMessage(50, 1, 1, "  ������̣�"+N1+" km");
					m_myHandler.sendMessage(msg);
					
					msg = m_myHandler
							.obtainMessage(51, 1, 1, "  �ٶȣ�"+N2+" km/h");
					m_myHandler.sendMessage(msg);
					
					msg = m_myHandler
							.obtainMessage(52, 1, 1, "  ��ѹ��"+N4*0.1+" V");
					m_myHandler.sendMessage(msg);
					
					msg = m_myHandler
							.obtainMessage(53, 1, 1, "  ������"+N5*0.01+" A");
					m_myHandler.sendMessage(msg);
					
					String N66;
					
					if(N6 < 0){
						N66 = "----";
					}else {
						N66 = String.valueOf(N6);
					}
					
					msg = m_myHandler
							.obtainMessage(54, 1, 1, "  ���������"+N66+" mAh");
					m_myHandler.sendMessage(msg);
					
			}else if (mingwen[0] == 0x05 && mingwen[1] == 0x24) {
					String GSMVer = null;
					byte[] GSMVersion = Arrays.copyOfRange(mingwen,2,mingwen.length);
					try {
						GSMVer = new String(GSMVersion,"ascii");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
						msg = m_myHandler
								.obtainMessage(28, 1, 1,
						GSMVer);
						m_myHandler.sendMessage(msg);
					
			}else if (mingwen[0] == 0x07 && mingwen[1] == 0x03) {
					if (mingwen[3] == 0x00) {
						m_xinMiYue = true;
						Message msg = m_myHandler.obtainMessage(2, 1, 1,
								"  �޸���Կ�ɹ�");
						m_myHandler.sendMessage(msg);
					} else {
						Message msg = m_myHandler.obtainMessage(2, 1, 1,
								"  �޸���Կʧ��");
						m_myHandler.sendMessage(msg);
					}
				} else if (mingwen[0] == (byte) 0xcb && mingwen[1] == 0x05
						&& mingwen[2] == 0x03) {
					byte[] sendMiMa2 = { 0x05, 0x04, 0x06, mima2[0], mima2[1],
							mima2[2], mima2[3], mima2[4], mima2[5], token[0],
							token[1], token[2], token[3], 0x00, 0x00, 0x00 };
					SendData(sendMiMa2);
				} else if (mingwen[0] == (byte) 0xcb && mingwen[1] == 0x07
						&& mingwen[2] == 0x01) {
					byte[] sendMiYue2 = { 0x07, 0x02, 0x08, key2[8], key2[9],
							key2[10], key2[11], key2[12], key2[13], key2[14],
							key2[15], token[0], token[1], token[2], token[3],
							0x00 };
					SendData(sendMiYue2);
				}
			}
		
		}
		
	};

	private final BluetoothGattCallback mGattCallbackOAD = new BluetoothGattCallback() {
		@Override
		public void onConnectionStateChange(BluetoothGatt gatt, int status,
				int newState) {
			if (newState == BluetoothProfile.STATE_CONNECTED) {
				gatt.discoverServices();
				getTime();
				Message mess = m_myHandler.obtainMessage(7, 1, 1, "  ������");
				m_myHandler.sendMessage(mess);

			} else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
				mBluetoothGatt.disconnect();
				mBluetoothGatt.close();
				mBluetoothGatt = null;
				getTime();
				Message mess = m_myHandler.obtainMessage(7, 1, 1, "  �ѶϿ�����");
				m_myHandler.sendMessage(mess);
			}
		}

		@Override
		public void onServicesDiscovered(BluetoothGatt gatt, int status) {

			if (status == BluetoothGatt.GATT_SUCCESS) {
				BluetoothGattService service = gatt
						.getService(OAD_SERVICE_UUID);
				List<BluetoothGattCharacteristic> mCharListOad = service
						.getCharacteristics();

				if (mCharListOad.size() == 2) {
					readCharacteristic = mCharListOad.get(0);
					writeCharacteristic = mCharListOad.get(1);
					writeCharacteristic
							.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
					m_myHandler.sendEmptyMessage(6);
				}
			}
		}

		@Override
		public void onDescriptorWrite(BluetoothGatt gatt,
				BluetoothGattDescriptor descriptor, int status) {
			mBusy = false;
		}

		@Override
		public void onDescriptorRead(BluetoothGatt gatt,
				BluetoothGattDescriptor descriptor, int status) {
			mBusy = false;
		}

		@Override
		public void onCharacteristicRead(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic, int status) {
			mBusy = false;
		}

		@Override
		public void onCharacteristicChanged(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic) {
			mBusy = false;
		}

		@Override
		public void onCharacteristicWrite(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic, int status) {
			mBusy = false;
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 288: {
				String result = data.getExtras().getString("result");
				InsertThread hereThread = new InsertThread(m_nowMac, result);
				new Thread(hereThread).start();
				break;
			}
			case 688: {
				String result = data.getExtras().getString("result");
				ShouQuanThread hereThread = new ShouQuanThread(m_nowMac, result);
				new Thread(hereThread).start();
				break;
			}
			case 788: {
				String result = data.getExtras().getString("result");
				QuMiYaoThread hereThread = new QuMiYaoThread(result);
				new Thread(hereThread).start();
				break;
			}
			case 188: {
				mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();

				bleScanFilters = new ArrayList<>();
				bleScanFilters.add(new ScanFilter.Builder().setServiceUuid(
						findServerUUID).build());

				bleScanSettings = new ScanSettings.Builder().build();

				if (mBluetoothGatt != null) {
					mBluetoothGatt.disconnect();
					mBluetoothGatt.close();
					mBluetoothGatt = null;
				}

				m_myData.device = null;
				m_myData.address = "";
				m_myData.count = 0;
				m_myData.name = "";
				m_myData.version = "";
				info.setText("");

				mBluetoothLeScanner.startScan(null, bleScanSettings,
						scanCallback);
				break;
			}
			default: {
				break;
			}
			}
		} else if (requestCode == 188) {
			Toast.makeText(this, "��Ҫ������", Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	private boolean checkFile() {
		File mDir = Environment
				.getExternalStoragePublicDirectory(FW_CUSTOM_DIRECTORY);
		String filepath = mDir.getAbsolutePath() + File.separator
				+ "upFile.bin";
		// Load binary file
		try {
			// Read the file raw into a buffer
			InputStream stream;

			File f = new File(filepath);
			stream = new FileInputStream(f);
			stream.close();
			return true;
		} catch (IOException e) {
			// Handle exceptions here
			updateInfo.append("File open failed: " + filepath + "\n");
			return false;
		}
	}

	private boolean loadFile() {
		boolean fSuccess = false;

		File mDir = Environment
				.getExternalStoragePublicDirectory(FW_CUSTOM_DIRECTORY);
		String filepath = mDir.getAbsolutePath() + File.separator
				+ "upFile.bin";
//		+ "upFile.bin";
		// Load binary file
		try {
			// Read the file raw into a buffer
			InputStream stream;

			File f = new File(filepath);
			stream = new FileInputStream(f);

			stream.read(mFileBuffer, 0, mFileBuffer.length);
			stream.close();
			fSuccess = true;
		} catch (IOException e) {
			// Handle exceptions here
			updateInfo.append("File open failed: " + filepath + "\n");
			return false;
		}

		// Show image info
		mFileImgHdr.ver = Conversion
				.buildUint16(mFileBuffer[5], mFileBuffer[4]);
		mFileImgHdr.len = Conversion
				.buildUint16(mFileBuffer[7], mFileBuffer[6]);
		mFileImgHdr.imgType = ((mFileImgHdr.ver & 1) == 1) ? 'B' : 'A';
		System.arraycopy(mFileBuffer, 8, mFileImgHdr.uid, 0, 4);

		return fSuccess;
	}

	private void startProgramming() {

		updateInfo.append("startProgramming\n");
		if (!loadFile()) {
			updateInfo.append("loadFile failed!\n");
			return;
		}

		mProgressBar.setProgress(0);

		updateInfo.append("Programming started\n");
		mProgramming = true;
		buttonUpgrade.setText("ȡ������");

		// Prepare image notification
		byte[] buf = new byte[OAD_IMG_HDR_SIZE + 2 + 2];
		buf[0] = Conversion.loUint16(mFileImgHdr.ver);
		buf[1] = Conversion.hiUint16(mFileImgHdr.ver);
		buf[2] = Conversion.loUint16(mFileImgHdr.len);
		buf[3] = Conversion.hiUint16(mFileImgHdr.len);
		System.arraycopy(mFileImgHdr.uid, 0, buf, 4, 4);

		// Send image notification

		readCharacteristic.setValue(buf);
		mBluetoothGatt.writeCharacteristic(readCharacteristic);

		// Initialize stats
		mProgInfo.reset();

		// Start the programming thread
		new Thread(new OadTask()).start();
	}

	private void stopProgramming() {
		mProgramming = false;
		buttonUpgrade.setText("��ʼ����");

		if (mProgInfo.iBlocks == mProgInfo.nBlocks) {
			updateInfo.setText("Programming complete!\n");
			mBluetoothGatt = null;
			restartBle();
		} else {
			updateInfo.append("Programming cancelled\n");
		}
	}

	private void programBlock() {
		if (!mProgramming)
			return;

		if (mProgInfo.iBlocks < mProgInfo.nBlocks) {
			mProgramming = true;
			String msg = new String();

			// Prepare block
			mOadBuffer[0] = Conversion.loUint16(mProgInfo.iBlocks);
			mOadBuffer[1] = Conversion.hiUint16(mProgInfo.iBlocks);
			System.arraycopy(mFileBuffer, mProgInfo.iBytes, mOadBuffer, 2,
					OAD_BLOCK_SIZE);
			

			// Send block
			mBusy = true;
			writeCharacteristic.setValue(mOadBuffer);
			boolean success = mBluetoothGatt
					.writeCharacteristic(writeCharacteristic);

			if (success) {
				// Update stats
				mProgInfo.iBlocks++;
				mProgInfo.iBytes += OAD_BLOCK_SIZE;

				m_myHandler.sendEmptyMessage(8);

				if (!waitIdle(GATT_WRITE_TIMEOUT)) {
					mProgramming = false;
					success = false;
					msg = "GATT write timeout\n";
				}
			} else {
				mProgramming = false;
				msg = "GATT writeCharacteristic failed\n";
			}
			if (!success) {
				Message mess = m_myHandler.obtainMessage(7, 1, 1, msg);
				m_myHandler.sendMessage(mess);
			}
		} else {
			mProgramming = false;
		}

		if (!mProgramming) {
			runOnUiThread(new Runnable() {
				public void run() {
					stopProgramming();
				}
			});
		}
	}

	private class OadTask implements Runnable {
		@Override
		public void run() {
			while (mProgramming) {
				try {
					Thread.sleep(SEND_INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < BLOCKS_PER_CONNECTION & mProgramming; i++) {
					programBlock();
				}
			}
		}
	}

	public boolean waitIdle(int timeout) {
		timeout /= 10;
		while (--timeout > 0) {
			if (mBusy)
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			else
				break;
		}

		return timeout > 0;
	}

	class InsertThread implements Runnable {
		private String mac, ma;

		public InsertThread(String imac, String ima) {
			mac = imac;
			ma = ima;
		}

		@Override
		public void run() {

			try {
				// ʹ��get��ʽ
				
				mac = "0102" + mac.replaceAll(":", "").toLowerCase();
								String hereUri = "mac="
						+ mac + "&ma=" + ma;

				HttpGet httpRequest = new HttpGet(hereUri);

				HttpResponse httpResponse = new DefaultHttpClient()
						.execute(httpRequest);

				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

					Message msg = m_myHandler.obtainMessage(2, 1, 1, "�ϴ��ɹ�");
					m_myHandler.sendMessage(msg);

				} else {
					Message msg = m_myHandler.obtainMessage(2, 1, 1, "�ϴ�ʧ��");
					m_myHandler.sendMessage(msg);
				}

			} catch (Exception e) {
				Message msg = m_myHandler.obtainMessage(2, 1, 1,
						"�ϴ�ʧ�ܣ�" + e.getMessage());
				m_myHandler.sendMessage(msg);
			}
		}
	}


	class ShouQuanThread implements Runnable {
		private String mac, ma;

		public ShouQuanThread(String imac, String ima) {
			mac = imac;
			ma = ima;
		}

		@Override
		public void run() {

			try {
				// ʹ��get��ʽ
				
				mac =  mac.replaceAll(":", "").toUpperCase();
				ma = URLEncoder.encode(ma);
				String hereUri = "="
						+ ma + "&data2=" + mac;

				HttpGet httpRequest = new HttpGet(hereUri);

				HttpResponse httpResponse = new DefaultHttpClient()
						.execute(httpRequest);

				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

					Message msg = m_myHandler.obtainMessage(2, 1, 1, "�ϴ��ɹ�");
					m_myHandler.sendMessage(msg);

				} else {
					Message msg = m_myHandler.obtainMessage(2, 1, 1, "�ϴ�ʧ��");
					m_myHandler.sendMessage(msg);
				}

			} catch (Exception e) {
				Message msg = m_myHandler.obtainMessage(2, 1, 1,
						"�ϴ�ʧ�ܣ�" + e.getMessage());
				m_myHandler.sendMessage(msg);
			}
		}
	}


	class QuMiYaoThread implements Runnable {
		private String ma;

		public QuMiYaoThread(String ima) {
			ma = ima;
		}

		@Override
		public void run() {

			try {
				// ʹ��get��ʽ
				
				String[] temp = ma.split("[?]");

				String hereUri = "?"
						+ temp[1];

				HttpGet httpRequest = new HttpGet(hereUri);

				HttpResponse httpResponse = new DefaultHttpClient()
						.execute(httpRequest);

				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String strResult = EntityUtils.toString(
							httpResponse.getEntity(), "GB2312");

					int startIndex = strResult.indexOf("currentKey");
					int startIndex2 = strResult.indexOf("password");
					
					if(startIndex > 0)
					{
						int endIndex = strResult.indexOf("\"",startIndex+13);
						int endIndex2 = strResult.indexOf("\"",startIndex2+11);
						
						String mima22 = strResult.substring(startIndex+13, endIndex);
						String[] items = mima22.split(",");
						if(items.length == 16)
						{
							for(int i = 0; i < 16; i++)
							{
								key[i] = Byte.parseByte(items[i]);
							}


							String mima33 = strResult.substring(startIndex2+11, endIndex2);
							String[] items2 = mima33.split(",");
							if(items2.length == 6)
							{
								for(int i = 0; i < 6; i++)
								{
									mima[i] = Byte.parseByte(items2[i]);
								}
								Message msg = m_myHandler.obtainMessage(2, 1, 1, "��ȡ�ɹ�");
								m_myHandler.sendMessage(msg);
							}
							else
							{
								Message msg = m_myHandler.obtainMessage(2, 1, 1, "��ȡʧ��");
								m_myHandler.sendMessage(msg);
							}
						}
						else
						{
							Message msg = m_myHandler.obtainMessage(2, 1, 1, "��ȡʧ��");
							m_myHandler.sendMessage(msg);
						}
						

					}
					else
					{
						Message msg = m_myHandler.obtainMessage(2, 1, 1, "��ȡʧ��");
						m_myHandler.sendMessage(msg);
					}

				} else {
					Message msg = m_myHandler.obtainMessage(2, 1, 1, "��ȡʧ��");
					m_myHandler.sendMessage(msg);
				}

			} catch (Exception e) {
				Message msg = m_myHandler.obtainMessage(2, 1, 1,
						"��ȡʧ�ܣ�" + e.getMessage());
				m_myHandler.sendMessage(msg);
			}
		}
	}

	private class ImgHdr {
		short ver;
		short len;
		Character imgType;
		byte[] uid = new byte[4];
	}

	private class ProgInfo {
		int iBytes = 0; // Number of bytes programmed
		short iBlocks = 0; // Number of blocks programmed
		short nBlocks = 0; // Total number of blocks

		void reset() {
			iBytes = 0;
			iBlocks = 0;
			nBlocks = (short) (mFileImgHdr.len / (OAD_BLOCK_SIZE / HAL_FLASH_WORD_SIZE));
		}
	}


}
