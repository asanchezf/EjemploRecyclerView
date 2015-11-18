package com.antonioejemplos.ejemplorecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/*SOliver...
* Este nuevo control se llama RecyclerView y, al igual que conseguímos con ListView y GridView,
* nos va a permitir mostrar en pantalla colecciones grandes de datos. Pero lo va a hacer de una
* forma algo distinta a la que estábamos habituados con los controles anteriores. Y es que RecyclerView
* no va a hacer “casi nada” por sí mismo, sino que se va a sustentar sobre otros componentes complementarios
* para determinar cómo acceder a los datos y cómo mostrarlos. Los más importantes serán los siguientes:

RecyclerView.Adapter
RecyclerView.ViewHolder
LayoutManager
ItemDecoration
ItemAnimator

De igual forma que hemos hecho con los componentes anteriores, un RecyclerView se apoyará también en un
adaptador para trabajar con nuestros datos, en este caso un adaptador que herede de la clase RecyclerView.Adapter.
La peculiaridad en esta ocasión es que este tipo de adaptador nos “obligará” en cierta medida a utilizar el patrón View Holder
y de ahí la necesidad del segundo componente de la lista anterior, RecyclerView.ViewHolder.
LayoutManager:  Una vista de tipo RecyclerView por el contrario no determina por sí sola la forma en que se van a mostrar en
pantalla los elementos de nuestra colección, sino que va a delegar esa tarea a otro componente llamado LayoutManager,
que también tendremos que crear y asociar al RecyclerView para su correcto funcionamiento. Por suerte, el SDK incorpora de serie
tres LayoutManager para las tres representaciones más habituales: lista vertical u horizontal (LinearLayoutManager),
tabla tradicional (GridLayoutManager) y tabla apilada o de celdas no alineadas (StaggeredGridLayoutManager). Por tanto, siempre
que optemos por alguna de estas distribuciones de elementos no tendremos que crear nuestro propio LayoutManager personalizado,
aunque por supuesto nada nos impide hacerlo, y ahí uno de los puntos fuertes del nuevo componente: su flexibilidad.
Los dos últimos componentes de la lista se encargarán de definir cómo se representarán algunos aspectos visuales concretos de
nuestra colección de datos (más allá de la distribución definida por el LayoutManager), por ejemplo marcadores o separadores
de elementos, y de cómo se animarán los elementos al realizarse determinadas acciones sobre la colección, por ejemplo al añadir
o eliminar elementos.
No siempre será obligatorio implementar todos estos componentes para hacer uso de un RecyclerView. Lo más habitual será implementar
el Adapter y el ViewHolder, utilizar alguno de los LayoutManager predefinidos, y sólo en caso de necesidad crear los ItemDecoration
e ItemAnimator necesarios para dar un toque de personalización especial a nuestra aplicación.
* */


public class MainActivity extends AppCompatActivity {

    private RecyclerView recView;
    private Button btnInsertar;
    private Button btnEliminar;
    private Button btnMover;
    private ArrayList<Titular> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recView=(RecyclerView)findViewById(R.id.RecView);
        btnInsertar = (Button)findViewById(R.id.BtnInsertar);
        btnEliminar = (Button)findViewById(R.id.BtnEliminar);
        btnMover = (Button)findViewById(R.id.BtnMover);



        datos=new ArrayList<Titular>();

        for (int i=0;i<50;i++){

            datos.add(new Titular("Título" +i,"Subtítulo"+i));
        }


        recView.setHasFixedSize(true);//Sabemos su tamaño máximo y poniendo esto optimizamos.
        final AdaptadorTitulares adaptador=new AdaptadorTitulares(datos);//Creamos una instancia del adaptador
        recView.setAdapter(adaptador);//Asignamos el adaptador al ReciclerView.

        //Evento del ReciclerView
        recView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Ejemplo ReciclerView", "PULSANDO EL ELEMENTO" + recView.getChildAdapterPosition(v));

            }
        });

        //Le asignamos un layout al ReciclerView
        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //recView.setLayoutManager(new GridLayoutManager(this,3));//Modo GridView

        //ItemDecoration e itemAnimator
        recView.addItemDecoration(
                new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));

        recView.setItemAnimator(new DefaultItemAnimator());

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datos.add(1, new Titular("Nuevo Titulo", "Subtítulo nuevo titular"));
                adaptador.notifyItemInserted(1);//Comunicamos al adaptador que se realizado un alta para que lance el evento de animación oportuno.

            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datos.remove(1);
                adaptador.notifyItemRemoved(1);//Comunicamos al adaptador
            }
        });


        btnMover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Titular aux = datos.get(1);
                datos.set(1,datos.get(2));
                datos.set(2,aux);

                adaptador.notifyItemMoved(1, 2);//Comunic<cmos al adpatador
            }
        });
    }
}
