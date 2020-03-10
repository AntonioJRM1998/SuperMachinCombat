package com.mygdx.machine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.machine.basedatos.BaseDeDatosAndroid;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Main(new BaseDeDatosAndroid(this)), config);
        Toast.makeText(this,"Bienvendio",Toast.LENGTH_LONG).show();
	}
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setMessage("¿Quieres salir de SuperMachineCombat?");

        alert.setCancelable(false);
        alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.show();
    }
}
