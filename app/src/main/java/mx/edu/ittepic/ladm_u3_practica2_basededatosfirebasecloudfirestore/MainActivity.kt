package mx.edu.ittepic.ladm_u3_practica2_basededatosfirebasecloudfirestore

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.size
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    var remoteDB = FirebaseFirestore.getInstance()

    //arreglo para guardar los datos del cliente y pedido
    var dataOrder = ArrayList<String>()

    //Arreglo para almecenar el ID
    var listID = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Realiza tu pedido")

        btnMakeOrder.setOnClickListener {
            makeOrder()
        }//End of button make Order

        btnSearchOrder.setOnClickListener {
            var winSearch = Intent(this,Main3Activity::class.java)

            startActivity(winSearch)
        }


        //Esto es similiar a un select ya que tomara toda la informacion del documento sin filtrado
        remoteDB.collection("restaurant")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    //Si es diferente de null siginifica que existe un error
                    Toast.makeText(
                        this,
                        "NO SE PUDO REALIZAR LA CONSULTA CON LA BD",
                        Toast.LENGTH_LONG
                    ).show()
                    return@addSnapshotListener
                }
                dataOrder.clear()
                listID.clear()

                //Este ciclo recorrera cada uno de los documentos de la colleccion
                for (document in querySnapshot!!) {
                    //Guardara todos los campos en un solo renglon que almacenaremos en nuestro arreglo
                    var data = "Nombre: " + document.getString("nombre") + "\n" +
                            "Domicilio: " + document.getString("domicilio") + "\n" +
                            "Descripcion: " + document.get("Pedido.descripcion") + "\n" +
                            "Cantidad: " + document.get("Pedido.cantidad")
                    dataOrder.add(data)

                    listID.add(document.id)
                }

                //Si el documento esta vacio
                if (dataOrder.size == 0) {
                    dataOrder.add("No hay pedidos")
                }

                //Con esto recupera la data en la nube que ese extrajo anteriormente y la muestra en el listView
                var adaptador = ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_expandable_list_item_1,
                    dataOrder
                )
                orderList.adapter = adaptador
            }

        orderList.setOnItemClickListener { adapterView, view, position, id ->
            if (listID.size == 0) {
                return@setOnItemClickListener
            }
            AlertDeleteUpdate(position)
        }//Fin del oyente de los items de la lista


        stateOrder()

    }//End of onCreate

    private fun stateOrder() {
        var estado = findViewById<TextView>(R.id.state)
        var barra = findViewById<Switch>(R.id.orderStatus)

        if (barra.isChecked) {
            estado.setText("Entregado")
        }
        if (!barra.isChecked) {
            estado.setText("NO entregado")
        }

    }

    private fun makeOrder() {

        var dataInsert = hashMapOf(
            "nombre" to name.text.toString(),
            "domicilio" to address.text.toString(),
            "telefono" to telephone.text.toString(),
            "Pedido" to hashMapOf(
                "descripcion" to description.text.toString(),
                "precio" to price.text.toString().toFloat(),
                "cantidad" to quantity.text.toString().toInt(),
                "entregado" to orderStatus.isChecked
            )//subDocument
        )//End of document

        remoteDB.collection("restaurant")
            .add(dataInsert)
            .addOnSuccessListener {
                Toast.makeText(this,"SE CAPTURO EL PEDIDO CORRECTAMENTE", Toast.LENGTH_LONG).show()
                cleanText()
            }
            .addOnFailureListener {
                Toast.makeText(this,"ERROR NO SE PUDO REALIZAR EL PEDIDO", Toast.LENGTH_LONG).show()
            }

    }//End of method insert

    private fun AlertDeleteUpdate(position:Int){
        AlertDialog.Builder(this)
            .setTitle("Atencion")
            .setMessage("Que deseas hacer con\n${dataOrder[position]} ")
            .setPositiveButton("Eliminar"){d,w ->
                delete(listID[position])
            }
            .setNegativeButton("Actualizar"){d,w->
                callWindowUpdate(listID[position])
            }
            .setNeutralButton("Cancelar"){ dialog, witch ->}
            .show()
    }//End of method AlertDelete

    private fun callWindowUpdate(idUpdate: String) {
        remoteDB.collection("restaurant")
            .document(idUpdate)
            .get()
            .addOnSuccessListener {
                var acti2 = Intent(this,Main2Activity::class.java)
                acti2.putExtra("id",idUpdate)
                acti2.putExtra("nombre", it.getString("nombre"))
                acti2.putExtra("domicilio", it.getString("domicilio"))
                acti2.putExtra("telefono", it.getString("telefono"))

                acti2.putExtra("descripcion", it.getString("Pedido.descripcion"))
                acti2.putExtra("precio", it.get("Pedido.precio").toString())
                acti2.putExtra("cantidad", it.get("Pedido.cantidad").toString())
                acti2.putExtra("entregado", it.getBoolean("Pedido.entregado"))

                startActivity(acti2)
            }
            .addOnFailureListener {
                Toast.makeText(this,"Error no hay conexion de red",Toast.LENGTH_LONG).show()
            }
    }

    private fun delete(idDelete: String) {
        remoteDB.collection("restaurant")
            .document(idDelete)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this,"El pedido ha sido eliminado",Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(this,"No se pudo eliminar el pedido",Toast.LENGTH_LONG).show()
            }
    }

    fun cleanText(){
        name.setText("")
        address.setText("")
        telephone.setText("")
        description.setText("")
        price.setText("")
        quantity.setText("")
        orderStatus.isChecked = false
    }
}
