package com.example.jeanpaucar.litorobotapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.jeanpaucar.litorobotapp.util.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Esta clase realiza todo el trabajo para configurar y administrar la conexion Bluetooth con otro dispositivo.
 * Hay un hilo que esta a la escucha de conexiones entrantes, un hilo para conexiones con un dispositivo y
 * un hilo para realizar las transmisiones una vez que se este conectado.
 */
public class BluetoothConexion {

    final String TAG_BLUETOOTH_CONN = "BluetoothConn";
    // Debug Para monitorizar los eventos
    // Nombre para el registro SDP cuando el socket sea creado
    private static final String NAME = "BluetoothDEB";

    // UUID Identificador unico de URI para esta aplicacion
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    //private static final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66"); // UUID para chat con otro Android
    //private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // UUID para modulos BT RN42

    // Constantes que indican el estado de conexion
    public static final int STATE_NONE = 0;       // No se esta haciendo nada
    public static final int STATE_LISTEN = 1;     // Escuchando conexiones entrantes
    public static final int STATE_CONNECTING = 2; // Iniciando conexion saliente
    public static final int STATE_CONNECTED = 3;  // Conectado con un dispositivo
    public static final int STATE_DISCONNECTED = 4;  // Conectado con un dispositivo
    public static final int STATE_ERROR = 5;  // Error de conexion

    public static final int Mensaje_Estado_Cambiado = 10;
    public static final int Mensaje_Leido = 11;
    public static final int Mensaje_Escrito = 12;
    public static final int Mensaje_Nombre_Dispositivo = 13;
    public static final int Mensaje_Toast = 14;
    public static final String DEVICE_NAME = "device_name";
    public static final String RESPONSE_FROM_CAR = "ResponseFromCar";
    public static final String TOAST = "toast";


    public static final String MESSAGE_NONE = "None";
    public static final String MESSAGE_LISTEN = "Listen";
    public static final String MESSAGE_CONNECTING = "Connecting";
    public static final String MESSAGE_CONNECTED = "Connected";
    public static final String MESSAGE_DISCONNECTED = "Disconnected";
    public static final String MESSAGE_ERROR = "Error";
    public static final String MESSAGE_TURN_ON_BLUETOOTH = "TurnOnBluetooth";
    ////////////////////////////////////////////////////////

    // Campos de conexion
    private final BluetoothAdapter AdaptadorBT;
    private final Handler mHandler;
    private AcceptThread HebraDeAceptacion;
    private ConnectThread HiloDeConexion;
    private ConnectedThread HiloConectado;
    private int EstadoActual = STATE_NONE;

    /**
     * Constructor. Prepara una nueva sesion para la conexion Bluetooth Smartphone-Dispositivo
     *
     * @param context El identificador UI de la actividad de context
     * @param handler Un Handler para enviar mensajes de regreso a la actividad marcada por el UI
     */
    public BluetoothConexion(Context context, Handler handler) {

        AdaptadorBT = BluetoothAdapter.getDefaultAdapter();
        mHandler = handler;
    }

    /**
     * Inicia el HiloConectado para iniciar la conexion con un dispositivo remoto
     *
     * @param device -->El dispositivo BT a conectar
     */
    public synchronized void connect(BluetoothDevice device) {

        //Cancela cualquier hilo que intente realizar una conexion
        if (EstadoActual == STATE_CONNECTING) {
            if (HiloDeConexion != null) {
                HiloDeConexion.cancel();
                HiloDeConexion = null;
            }
        }

        //Cancela cualquier hilo que se encuentre corriendo una conexion
        if (HiloConectado != null) {
            HiloConectado.cancel();
            HiloConectado = null;
        }

        //Inicia el hilo para conectar con un dispositivo
        HiloDeConexion = new ConnectThread(device);
        HiloDeConexion.start();
        StatusConnecting(device);
    }

    /**
     * Inicia el servicio bluetooth. Especificamente inicia la HebradeAceptacion para iniciar el
     * modo de "listening". LLamado por la Actividad onResume()
     */
    public synchronized void start() {
        Log.d(TAG_BLUETOOTH_CONN, "start");

        //Cancela cualquier hilo que quiera hacer una conexion
        if (HiloDeConexion != null) {
            HiloDeConexion.cancel();
            HiloDeConexion = null;
        }

        //Cancela cualquier hebra que este corriendo una conexion
        if (HiloConectado != null) {
            HiloConectado.cancel();
            HiloConectado = null;
        }

        // Inicia la hebra que escuchara listen en el BluetoothServerSocket
        if (HebraDeAceptacion == null) {
            HebraDeAceptacion = new AcceptThread();
            HebraDeAceptacion.start();
        }
        setState(STATE_LISTEN);
    }

    /**
     * Actualizamos estado de la conexion BT a la actividad
     *
     * @param estado Un entero definido para cada estado
     */
    private synchronized void setState(int estado) {
        this.EstadoActual = estado;
    }

    /**
     * Regresa el estado de la conexion
     */
    public synchronized int getState() {
        return EstadoActual;
    }

    /**
     * Inicia la hebra conectada para iniciar la administracion de la conexión BT
     *
     * @param socket El socket Bt donde se realizara la conexion
     * @param device El dispositivo BT con que se conectara
     */
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {

        // Cancela el hilo  que completo la conexion
        if (HiloDeConexion != null) {
            HiloDeConexion.cancel();
            HiloDeConexion = null;
        }

        //Cancela el hilo que actualmente esta corriendo la conexion 
        if (HiloConectado != null) {
            HiloConectado.cancel();
            HiloConectado = null;
        }

        // Cancela la Hebradeaceptacion debido a que solo queremos conectar con un dispositivo**********
        if (HebraDeAceptacion != null) {
            HebraDeAceptacion.cancel();
            HebraDeAceptacion = null;
        }

        //Inicia el hilo para administrar la conexion y realizar transmisiones
        HiloConectado = new ConnectedThread(socket);
        HiloConectado.start();
        StatusConnected(device);
    }

    /**
     * Para todos los hilos y pone el estado de STATE_NONE donde no esta haciendo nada
     */
    public synchronized void stop() {
        Log.e(TAG_BLUETOOTH_CONN, "stop");
        if (HiloDeConexion != null) {
            HiloDeConexion.cancel();
            HiloDeConexion = null;
        }
        if (HiloConectado != null) {
            HiloConectado.cancel();
            //HiloConectado = null;
        }
        if (HebraDeAceptacion != null) {
            HebraDeAceptacion.cancel();
            HebraDeAceptacion = null;
        }
        setState(STATE_NONE);
    }

    /**
     * Escribe en el HiloConectado de manera Asincrona
     *
     * @param out Los bytes a escribir
     * @see ConnectedThread#write(byte[])
     */
    public void write(byte[] out) {
        ConnectedThread r; //Creacion de objeto temporal
        // Syncronizar la copia del HiloConectado
        synchronized (this) {
            if (EstadoActual != STATE_CONNECTED) return;
            r = HiloConectado;
        }
        // Realizar la escritura Asincrona
        r.write(out);
    }

    /**
     * Indica el proceso de conexion
     */
    private void StatusConnected(BluetoothDevice deviceTemp) {

        setState(STATE_CONNECTED);
        //Envia un mensaje de falla de vuelta a la actividad
        Message msg = mHandler.obtainMessage(STATE_CONNECTED);
        Bundle bundle = new Bundle();
        bundle.putString(DEVICE_NAME, deviceTemp.getName());
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    /**
     * Indica el proceso de conexion
     */
    private void StatusConnecting(BluetoothDevice deviceTemp) {

        setState(STATE_CONNECTING);
        //Envia un mensaje de falla de vuelta a la actividad
        Message msg = mHandler.obtainMessage(STATE_CONNECTING);
        Bundle bundle = new Bundle();
        bundle.putString(DEVICE_NAME, deviceTemp.getName());
        msg.setData(bundle);
        mHandler.sendMessage(msg);
        Log.d(TAG_BLUETOOTH_CONN, "Connecting with " + deviceTemp.getName());
    }

    /**
     * Indica que el intento de conexion fallo y notifica a la actividad WidgetProvider/UpdateService
     */
    private void connectionFailed() {

        setState(STATE_ERROR); //setState(STATE_LISTEN);
        //Envia un mensaje de falla de vuelta a la actividad
        Message msg = mHandler.obtainMessage(STATE_ERROR);
        Bundle bundle = new Bundle();
        bundle.putString(TOAST, "Error de conexión");
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    /**
     * Indica que la conexion se perdio y notifica a la UI activity(WidgetProvider/UpdateSErvice)
     */
    private void connectionLost() {

        setState(STATE_DISCONNECTED); //setState(STATE_LISTEN);
        //Envia un mensaje de falla de vuelta a la actividad 
        Message msg = mHandler.obtainMessage(STATE_DISCONNECTED);
        Bundle bundle = new Bundle();
        bundle.putString(TOAST, "Connection failed!");
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    /**
     * Este hilo corre mientras se este ESCUCHANDO por conexiones entrantes. Este se
     * comporta como el lado-Servidor cliente. Corre mientras la conexion es aceptada(o cancelada)
     */
    private class AcceptThread extends Thread {
        // El soket de servidor Local
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            //Creamos un nuevo listening server socket
            try {
                tmp = AdaptadorBT.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } catch (IOException e) {
                tmp = null; //////Jean
                Log.e(TAG_BLUETOOTH_CONN, "listen() fallo", e);
            }
            mmServerSocket = tmp;
        }

        public void run() {

            if (mmServerSocket == null) {//////////
                return;///////////////Jean
            }////////////////////////
            Log.e(TAG_BLUETOOTH_CONN, "Comenzar HiloDeAceptacion " + this);
            setName("HiloAceptado");
            BluetoothSocket socket = null;
            //Escucha al server socket si no estamos conectados
            while (EstadoActual != STATE_CONNECTED) {
                try {
                    //Esto es un  bloque donde solo obtendremos una conexion o una excepcion
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    socket = null; ////////////Jean
                    Log.e(TAG_BLUETOOTH_CONN, "accept() failed", e);
                    break;
                }
                //Si la conexion fue aceptada...
                if (socket != null) {
                    synchronized (BluetoothConexion.this) {
                        switch (EstadoActual) {
                            case STATE_LISTEN:
                            case STATE_CONNECTING:
                                // Situation normal. Iniciamos HebraConectada
                                connected(socket, socket.getRemoteDevice());
                                break;
                            case STATE_NONE:
                            case STATE_CONNECTED:
                                // O no esta lista o ya esta conectado. Termina el nuevo socket
                                try {
                                    socket.close();
                                } catch (IOException e) {
                                    Log.e(TAG_BLUETOOTH_CONN, "No se pudo cerrar el socket no deseado", e);
                                }
                                break;
                        }
                    }
                }
            }
            Log.e(TAG_BLUETOOTH_CONN, "Fin de HIlodeAceptacion");
        }

        public void cancel() {
            Log.e(TAG_BLUETOOTH_CONN, "Cancela " + this);
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.e(TAG_BLUETOOTH_CONN, "close() del servidor FAllo", e);
            }
        }
    }

    /**
     * Esta Hebra correra mientras se intente realizar una conexion de salida con un dispositivo.
     * Este correra a través de la conexion ya sea establecida o fallada
     */
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            BluetoothSocket tmp = null;
            // Obtiene un BluetoothSocket para la conexion con el Dispositivo obtenido
            try {
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
                Log.d(TAG_BLUETOOTH_CONN, "BluetoothSocket available");
            } catch (IOException e) {
                Log.e(TAG_BLUETOOTH_CONN, "Connection attempt failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {

            Log.d(TAG_BLUETOOTH_CONN, "Starting connection, trying to connect sockets");
            setName("HiloConectado");

            AdaptadorBT.cancelDiscovery(); //Siempre cancela la busqueda debido a que esta hara lenta la conexion

            try { // Realiza la conexion con el socketBluetooth
                mmSocket.connect(); // Aqui solo recibiremos o una conexion establecida o una excepcion
                Log.d(TAG_BLUETOOTH_CONN, "BluetoothSocket connected");

            } catch (IOException e) {

                try { // Cierra el socket
                    mmSocket.close();
                    Log.e(TAG_BLUETOOTH_CONN, "Socket closed after connection failed");
                } catch (IOException e2) {
                    Log.e(TAG_BLUETOOTH_CONN, "Imposible cerrar el socket durante la falla de conexion", e2);
                }

                connectionFailed();
                // Inicia el servicio a traves de reiniciar el modo de listening
                // Comentado porque no es necesario ponerle en modo escucha en este Hilo : ConnectThread
                //////////////////////////////BluetoothConexion.this.start(); ///////////////////////////////////
                //////////////////////////////Log.d(TAG_BLUETOOTH_CONN, "BluetoothConexion Start");////////////////
                return;
            }
            // Resetea el HiloConectado pues ya lo hemos usado
            synchronized (BluetoothConexion.this) {
                HiloDeConexion = null;
                Log.d(TAG_BLUETOOTH_CONN, "Resetting ConnectThread that It was starting");
            }
            connected(mmSocket, mmDevice);  // Inicia el hiloconectado
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG_BLUETOOTH_CONN, "close() of connect socket failed", e);
            }
        }
    }

    /**
     * Este hilo corre durante la conexion con un dispositivo remoto.
     * Este maneja todas las transmisiones de entrada y salida.
     */
    private class ConnectedThread extends Thread {

        private final BluetoothSocket BTSocket;
        private final InputStream INPUT_Stream;
        private final OutputStream OUTPUT_Stream;

        public ConnectedThread(BluetoothSocket socket) {

            BTSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            // Obtencion del BluetoothSocket de entrada y saldida
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
                Log.d(TAG_BLUETOOTH_CONN, "Sockets Input/Output created");
            } catch (IOException e) {
                Log.e(TAG_BLUETOOTH_CONN, "Sockets temporales No creados", e);
            }
            INPUT_Stream = tmpIn;
            OUTPUT_Stream = tmpOut;
        }

        /**
         * Escribe al Stream de salida conectado
         *
         * @param buffer Los bytes a escribir
         */
        public void write(byte[] buffer) {

            try {
                OUTPUT_Stream.write(buffer); //Compartir el mensaje enviado con la UI activity
                mHandler.obtainMessage(Mensaje_Escrito, -1, -1, buffer).sendToTarget();
            } catch (IOException e) {
                Log.e(TAG_BLUETOOTH_CONN, "Exception during write", e);
            }
        }

        public void cancel() {
            try {
                BTSocket.close();
            } catch (IOException e) {
                Log.e(TAG_BLUETOOTH_CONN, "close() del socket conectado Fallo", e);
            }
        }

        public void run() {

            LogUtil.SaveLogDep(TAG_BLUETOOTH_CONN, "Starting Sockets connected");

            int bytes;

            while (true) { //Mantiene escuchando el InputStream mientras este conectado
                try {
                    //Lee desde el InputStream

                    if (BTSocket != null) {

                        if (BTSocket.isConnected()) {

                            LogUtil.SaveLogDep(TAG_BLUETOOTH_CONN, "Socket Connected - Receiving data");

                            byte[] buffer = new byte[100];
                            setState(STATE_CONNECTED);
                            bytes = INPUT_Stream.read(buffer);
                            //byte[] readBufX = (byte[]) buffer;  //Construye un String desde los bytes validos en el buffer
                            //String readMessageX = new String(readBufX, 0, bytes);//Se envia el readNessagexxx en lugar del buffer pues ya se PARSEO
                            //mHandler.obtainMessage(Mensaje_Leido, bytes, -1, buffer).sendToTarget(); //readMessageX por buffer

                            if (bytes > 0) {

                                String readMessage = new String(buffer, 0, bytes);

                                if (!readMessage.trim().equals("")) {

                                    Message msg = mHandler.obtainMessage(Mensaje_Leido);
                                    Bundle bundle = new Bundle();
                                    bundle.putString(RESPONSE_FROM_CAR, readMessage);
                                    msg.setData(bundle);
                                    mHandler.sendMessage(msg);
                                }
                            }

                        } else {

                            // Resetea el HiloConectado pues ya lo hemos usado
                            synchronized (BluetoothConexion.this) {
                                HiloConectado = null;
                                LogUtil.SaveLogError(TAG_BLUETOOTH_CONN, "Resetting ConnectedThread1");
                            }
                            connectionLost();
                            LogUtil.SaveLogError(TAG_BLUETOOTH_CONN, "Socket disconnected");
                        }
                    } else {

                        // Resetea el HiloConectado pues ya lo hemos usado
                        synchronized (BluetoothConexion.this) {
                            HiloConectado = null;
                            LogUtil.SaveLogError(TAG_BLUETOOTH_CONN, "Resetting ConnectedThread2");
                        }
                        connectionLost();
                        LogUtil.SaveLogError(TAG_BLUETOOTH_CONN, "Socket NULL");
                    }

                } catch (IOException e) {

                    // Resetea el HiloConectado pues ya lo hemos usado
                    synchronized (BluetoothConexion.this) {
                        HiloConectado = null;
                        LogUtil.SaveLogError(TAG_BLUETOOTH_CONN, "Resetting ConnectedThread3");
                    }
                    connectionLost();
                    LogUtil.SaveLogError(TAG_BLUETOOTH_CONN, "Socket disconnected with error:" + e);
                    break;
                }
            }
        }
    }
}
