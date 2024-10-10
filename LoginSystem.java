import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class LoginSystem {
    private HashMap<String, String> users = new HashMap<>();
    private List<Publication> publications = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<Product> cart = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem();
        loginSystem.run();
    }

    public void run() {
        // Agregamos algunos productos de ejemplo
        agregarProductosDeEjemplo();

        while (true) {
            System.out.println("1. Crear usuario");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");
            System.out.print("Elige una opción: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (choice) {
                case 1:
                    crearUsuario();
                    break;
                case 2:
                    iniciarSesion();
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }

    private void agregarProductosDeEjemplo() {
        products.add(new Product("Laptop", 1200.00));
        products.add(new Product("Smartphone", 800.00));
        products.add(new Product("Tablet", 300.00));
    }

    private void crearUsuario() {
        System.out.print("Introduce un nombre de usuario: ");
        String username = scanner.nextLine();
        if (users.containsKey(username)) {
            System.out.println("El usuario ya existe. Prueba con otro.");
            return;
        }

        System.out.print("Introduce una contraseña: ");
        String password = scanner.nextLine();
        users.put(username, password);
        System.out.println("Usuario creado con éxito.");
    }

    private void iniciarSesion() {
        System.out.print("Introduce tu nombre de usuario: ");
        String username = scanner.nextLine();
        System.out.print("Introduce tu contraseña: ");
        String password = scanner.nextLine();

        if (users.containsKey(username) && users.get(username).equals(password)) {
            System.out.println("Inicio de sesión exitoso. ¡Bienvenido, " + username + "!");
            menuCatalogo(username);
        } else {
            System.out.println("Nombre de usuario o contraseña incorrectos.");
        }
    }

    private void menuCatalogo(String username) {
        while (true) {
            System.out.println("=== Menú de Catálogos ===");
            System.out.println("1. Crear publicación");
            System.out.println("2. Ver publicaciones");
            System.out.println("3. Ver productos");
            System.out.println("4. Agregar al carrito");
            System.out.println("5. Ver carrito");
            System.out.println("6. Comprar");
            System.out.println("7. Cerrar sesión");
            System.out.print("Elige una opción: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (choice) {
                case 1:
                    crearPublicacion(username);
                    break;
                case 2:
                    verPublicaciones();
                    break;
                case 3:
                    verProductos();
                    break;
                case 4:
                    agregarAlCarrito();
                    break;
                case 5:
                    verCarrito();
                    break;
                case 6:
                    comprar();
                    break;
                case 7:
                    System.out.println("Cerrando sesión...");
                    return;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }

    private void crearPublicacion(String username) {
        System.out.print("Introduce el título de la publicación: ");
        String title = scanner.nextLine();
        System.out.print("Introduce la descripción de la publicación: ");
        String description = scanner.nextLine();

        Publication publication = new Publication(title, description, username);
        publications.add(publication);
        System.out.println("Publicación creada con éxito.");
    }

    private void verPublicaciones() {
        if (publications.isEmpty()) {
            System.out.println("No hay publicaciones disponibles.");
            return;
        }

        System.out.println("=== Publicaciones ===");
        for (Publication pub : publications) {
            System.out.println(pub);
        }
    }

    private void verProductos() {
        if (products.isEmpty()) {
            System.out.println("No hay productos disponibles.");
            return;
        }

        System.out.println("=== Productos ===");
        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". " + products.get(i));
        }
    }

    private void agregarAlCarrito() {
        verProductos();
        System.out.print("Selecciona el número del producto que deseas agregar al carrito: ");
        int productNumber = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        if (productNumber > 0 && productNumber <= products.size()) {
            cart.add(products.get(productNumber - 1));
            System.out.println("Producto agregado al carrito.");
        } else {
            System.out.println("Número de producto no válido.");
        }
    }

    private void verCarrito() {
        if (cart.isEmpty()) {
            System.out.println("El carrito está vacío.");
            return;
        }

        System.out.println("=== Carrito ===");
        for (Product product : cart) {
            System.out.println(product);
        }
    }

    private void comprar() {
        if (cart.isEmpty()) {
            System.out.println("No hay productos en el carrito para comprar.");
            return;
        }

        double total = 0;
        for (Product product : cart) {
            total += product.getPrice();
        }

        System.out.println("Total a pagar: $" + total);
        System.out.println("Compra realizada con éxito.");
        cart.clear(); // Vaciar el carrito después de la compra
    }

    private static class Publication {
        private String title;
        private String description;
        private String author;

        public Publication(String title, String description, String author) {
            this.title = title;
            this.description = description;
            this.author = author;
        }

        @Override
        public String toString() {
            return "Título: " + title + ", Descripción: " + description + ", Autor: " + author;
        }
    }

    private static class Product {
        private String name;
        private double price;

        public Product(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public double getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return "Producto: " + name + ", Precio: $" + price;
        }
    }
}
