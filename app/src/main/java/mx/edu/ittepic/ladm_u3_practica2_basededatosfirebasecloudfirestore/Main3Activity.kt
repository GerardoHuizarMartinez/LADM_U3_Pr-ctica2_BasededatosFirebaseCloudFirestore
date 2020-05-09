package mx.edu.ittepic.ladm_u3_practica2_basededatosfirebasecloudfirestore

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main3.*

class Main3Activity : AppCompatActivity() {

    var remoteDB = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        setTitle("Realize una busqueda")
        btnSearch.setOnClickListener{
            if (editValue.text.isEmpty()) {
                Toast.makeText(this, "No deje el campo vacio", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            makeQuery(editValue.text.toString(), spinnerKey.selectedItemPosition)
        }

        btnClose.setOnClickListener {
            finish()
        }

    }//End of on create




    private fun makeQuery(value: String, key: Int) {
        when (key) {
            0 -> {queryName(value)}
            1->{queryAddress(value)}
            2->{queryTelephone(value)}
            3->{queryDescription(value)}
            4->{queryPrice(value.toFloat())}
            5->{queryQuantity(value.toInt())}
            6->{queryState(value.toBoolean())}

        }
    }


    private fun queryName(value: String){
        remoteDB.collection("restaurant")
            .whereEqualTo("nombre", value)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->

                if (firebaseFirestoreException != null) {
                    Toast.makeText(this,"NO SE ENCONTRO EL NOMBRE VUELVA A INTENTARLO", Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }

                var res = ""
                for (document in querySnapshot!!) {
                    res += "Nombre: " + document.getString("nombre") + "\n"  +
                    "Domicilio: " + document.getString("domicilio") + "\n"+
                    "Telefono: " + document.getString("telefono") + "\n"+
                    "Descripcion: " + document.getString("Pedido.descripcion") + "\n"+
                    "Precio: " + document.get("Pedido.precio") + "\n"+
                    "Cantidad: " + document.get("Pedido.cantidad") + "\n"+
                    "Entregado: " + document.getBoolean("Pedido.entregado") + "\n\n"
                }

                queryResult.setText(res)
            }
    }

    private fun queryAddress(value: String){
        remoteDB.collection("restaurant")
            .whereEqualTo("domicilio", value)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->

                if (firebaseFirestoreException != null) {
                    Toast.makeText(this,"NO SE ENCONTRO EL NOMBRE VUELVA A INTENTARLO", Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }

                var res = ""
                for (document in querySnapshot!!) {
                    res += "Nombre: " + document.getString("nombre") + "\n"  +
                            "Domicilio: " + document.getString("domicilio") + "\n"+
                            "Telefono: " + document.getString("telefono") + "\n"+
                            "Descripcion: " + document.getString("Pedido.descripcion") + "\n"+
                            "Precio: " + document.get("Pedido.precio") + "\n"+
                            "Cantidad: " + document.get("Pedido.cantidad") + "\n"+
                            "Entregado: " + document.getBoolean("Pedido.entregado") + "\n\n"
                }

                queryResult.setText(res)
            }
    }

    private fun queryTelephone(value: String){
        remoteDB.collection("restaurant")
            .whereEqualTo("telefono", value)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->

                if (firebaseFirestoreException != null) {
                    Toast.makeText(this,"NO SE ENCONTRO EL NOMBRE VUELVA A INTENTARLO", Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }

                var res = ""
                for (document in querySnapshot!!) {
                    res += "Nombre: " + document.getString("nombre") + "\n"  +
                            "Domicilio: " + document.getString("domicilio") + "\n"+
                            "Telefono: " + document.getString("telefono") + "\n"+
                            "Descripcion: " + document.getString("Pedido.descripcion") + "\n"+
                            "Precio: " + document.get("Pedido.precio") + "\n"+
                            "Cantidad: " + document.get("Pedido.cantidad") + "\n"+
                            "Entregado: " + document.getBoolean("Pedido.entregado") + "\n\n"
                }

                queryResult.setText(res)
            }
    }

    private fun queryDescription(value: String){
        remoteDB.collection("restaurant")
            .whereEqualTo("Pedido.descripcion", value)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->

                if (firebaseFirestoreException != null) {
                    Toast.makeText(this,"NO SE ENCONTRO EL NOMBRE VUELVA A INTENTARLO", Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }

                var res = ""
                for (document in querySnapshot!!) {
                    res += "Nombre: " + document.getString("nombre") + "\n"  +
                            "Domicilio: " + document.getString("domicilio") + "\n"+
                            "Telefono: " + document.getString("telefono") + "\n"+
                            "Descripcion: " + document.getString("Pedido.descripcion") + "\n"+
                            "Precio: " + document.get("Pedido.precio") + "\n"+
                            "Cantidad: " + document.get("Pedido.cantidad") + "\n"+
                            "Entregado: " + document.getBoolean("Pedido.entregado") + "\n\n"
                }

                queryResult.setText(res)
            }
    }

    private fun queryPrice(value: Float){
        remoteDB.collection("restaurant")
            .whereGreaterThanOrEqualTo("Pedido.precio", value)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->

                if (firebaseFirestoreException != null) {
                    Toast.makeText(this,"NO SE ENCONTRO EL NOMBRE VUELVA A INTENTARLO", Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }

                var res = ""
                for (document in querySnapshot!!) {
                    res += "Nombre: " + document.getString("nombre") + "\n"  +
                            "Domicilio: " + document.getString("domicilio") + "\n"+
                            "Telefono: " + document.getString("telefono") + "\n"+
                            "Descripcion: " + document.getString("Pedido.descripcion") + "\n"+
                            "Precio: " + document.get("Pedido.precio") + "\n"+
                            "Cantidad: " + document.get("Pedido.cantidad") + "\n"+
                            "Entregado: " + document.getBoolean("Pedido.entregado") + "\n\n"
                }

                queryResult.setText(res)
            }
    }

    private fun queryQuantity(value: Int){
        remoteDB.collection("restaurant")
            .whereLessThanOrEqualTo("Pedido.cantidad", value)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->

                if (firebaseFirestoreException != null) {
                    Toast.makeText(this,"NO SE ENCONTRO EL NOMBRE VUELVA A INTENTARLO", Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }

                var res = ""
                for (document in querySnapshot!!) {
                    res += "Nombre: " + document.getString("nombre") + "\n"  +
                            "Domicilio: " + document.getString("domicilio") + "\n"+
                            "Telefono: " + document.getString("telefono") + "\n"+
                            "Descripcion: " + document.getString("Pedido.descripcion") + "\n"+
                            "Precio: " + document.get("Pedido.precio") + "\n"+
                            "Cantidad: " + document.get("Pedido.cantidad") + "\n"+
                            "Entregado: " + document.getBoolean("Pedido.entregado") + "\n\n"
                }

                queryResult.setText(res)
            }
    }

    private fun queryState(value: Boolean){
        remoteDB.collection("restaurant")
            .whereEqualTo("Pedido.entregado", value)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->

                if (firebaseFirestoreException != null) {
                    Toast.makeText(this,"NO SE ENCONTRO EL NOMBRE VUELVA A INTENTARLO", Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }

                var res = ""
                for (document in querySnapshot!!) {
                    res += "Nombre: " + document.getString("nombre") + "\n"  +
                            "Domicilio: " + document.getString("domicilio") + "\n"+
                            "Telefono: " + document.getString("telefono") + "\n"+
                            "Descripcion: " + document.getString("Pedido.descripcion") + "\n"+
                            "Precio: " + document.get("Pedido.precio") + "\n"+
                            "Cantidad: " + document.get("Pedido.cantidad") + "\n"+
                            "Entregado: " + document.getBoolean("Pedido.entregado") + "\n\n"
                }

                queryResult.setText(res)
            }
    }


}//End class
