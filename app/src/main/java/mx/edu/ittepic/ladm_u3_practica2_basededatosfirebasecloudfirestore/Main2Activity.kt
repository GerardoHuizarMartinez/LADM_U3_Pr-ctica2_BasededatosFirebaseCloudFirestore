package mx.edu.ittepic.ladm_u3_practica2_basededatosfirebasecloudfirestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    var id =""
    var dataBase = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setTitle("Actualiza tu pedido")


        var extras = intent.extras

        id = extras?.getString("id").toString()

        main2name.setText(extras?.getString("nombre"))
        main2address.setText(extras?.getString("domicilio"))
        main2telephone.setText(extras?.getString("telefono"))
        main2description.setText(extras?.getString("descripcion"))
        main2price.setText(extras?.get("precio").toString())
        main2quantity.setText(extras?.get("cantidad").toString())
        main2orderStatus.isChecked =(extras?.getBoolean("entregado")!!)


        Main2btnUpdate.setOnClickListener {
            dataBase.collection("restaurant")
                .document(id)
                .update(
                    "nombre",main2name.text.toString(),
                    "domicilio",main2address.text.toString(),
                    "telefono", main2telephone.text.toString(),
                    "Pedido.descripcion" , main2description.text.toString(),
                    "Pedido.precio", main2price.text.toString().toFloat(),
                    "Pedido.cantidad", main2quantity.text.toString().toInt(),
                    "Pedido.entregado" , main2orderStatus.isChecked
                )
                .addOnSuccessListener {
                    Toast.makeText(this,"Los datos del pedido se actualizaron correctamente", Toast.LENGTH_LONG).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this,"Los datos no se pudieron actualizar", Toast.LENGTH_LONG).show()
                }
        }//End of button Update

        main2Back.setOnClickListener {
            finish()
        }

    }
}
