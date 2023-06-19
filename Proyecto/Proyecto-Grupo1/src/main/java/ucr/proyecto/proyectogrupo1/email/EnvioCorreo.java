package ucr.proyecto.proyectogrupo1.email;

import ucr.proyecto.proyectogrupo1.TDA.AVL;
import ucr.proyecto.proyectogrupo1.TDA.ListException;
import ucr.proyecto.proyectogrupo1.TDA.TreeException;
import ucr.proyecto.proyectogrupo1.domain.Sale;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.time.LocalDate;

public class EnvioCorreo {
    //Codigo de ejemplo
    public static void main(String[] args) throws EnvioCorreos.EmailExcepcion, TreeException, ListException {
        /*EnvioCorreos mEnvioCorreos = new EnvioCorreos();
        mEnvioCorreos.setEmailTo("zjeancarlo42@gmail.com");
        mEnvioCorreos.setSubject("Prueba");
        mEnvioCorreos.setContent("Este mensaje fue enviado por Java");*/

   /*     String ruta = "C:/Users/qzuni/Desktop/envios/img.jpg";
        File archivo = new File(ruta);
        mEnvioCorreos.setAttachmentFile(archivo);*/

        //mEnvioCorreos.createEmail();
        //mEnvioCorreos.sendEmail();

        /*
                product.add(new Product(
                "209 145 96",
                "209 145 96",
                "647 414 349 513 341 230 421 266 234 480 324 239 360 251 186 339 218 153 418 275 210 499 331 252 453 282 272 598 397 298 535 334 302 607 378 346 629 416 318 590 394 295 921 568 463 272 190 132 298 198 150 483 302 292 623 417 303 512 343 227 483 306 274 638 425 310 613 381 349 636 417 320 456 283 273 654 434 329 309 219 122 635 408 339 912 560 463 380 286 178 610 404 303 393 237 197 332 230 172 600 402 297 921 568 463 511 330 286 660 445 329 680 447 350 440 299 185 958 625 558 622 418 303 437 290 179",
                "629 406 329 626 413 312 628 415 310 275 211 96",
                "312 207 153 284 190 142 208 144 96",
                "262 175 119",
                "252 167 117",
                "684 452 348 516 343 231 498 332 285 495 330 211 612 409 301 658 443 329 614 412 307 645 428 318 625 418 304 642 426 327 628 423 313 577 367 321 530 374 265 616 411 310 488 342 245 620 407 310 639 425 317 613 387 340 662 445 334 472 309 210 590 394 295 447 299 195 404 254 202 452 302 249 360 255 157 457 307 251 442 295 245 603 402 301 455 306 250 353 251 152 312 207 158 310 209 154 296 200 143 316 214 157 326 214 167 324 216 160 305 201 152 322 212 164 450 299 246 459 307 258 414 279 167"));


         */
        LocalDate hoy = LocalDate.now();

        AVL a = Utility.getSale();
        a.add(new Sale(0, hoy, 117780288, "hola"));

        Utility.setSale(a);

        a = Utility.getSale();
        Sale b = (Sale) a.get(0);
        System.out.println(b);
    }
}
