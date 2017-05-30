package com.liwy.easyrouter.compile;

import com.google.auto.service.AutoService;
import com.liwy.easyrouter.annotation.Router;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@SupportedSourceVersion(SourceVersion.RELEASE_7)
@AutoService(Processor.class)
public class RouterProcessor extends AbstractProcessor{
    private Messager messager;
    private Filer filer;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> ret = new HashSet<>();
        ret.add(Router.class.getCanonicalName());
        System.out.println("liwy getSupportedAnnotationTypes-->" + Router.class.getCanonicalName());
        return ret;
    }
    private void debug(String msg) {
        messager.printMessage(Diagnostic.Kind.NOTE, msg);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()){
            debug("liwy no annotations");
            return false;
        }
        boolean hasRouter = false;
        Set<? extends Element> routerList = roundEnv.getElementsAnnotatedWith(Router.class);
        if (routerList != null && routerList.size() > 0){
            hasRouter = true;
        }
        if (hasRouter){
            generatorInit(roundEnv);
        }else{
            System.out.println("liwy oh no!! there is no Router annotation!!!!!!");
        }
        return false;
    }

    /**
     * add the Activity infio to Routers's maps by annotation of Router
     * @param roundEnv
     */
    public void generatorInit( RoundEnvironment roundEnv){
        System.out.println("liwy good! there is Router annotation!!!!!!");
        Set<? extends Element> routerList = roundEnv.getElementsAnnotatedWith(Router.class);
        // 创建init方法
        MethodSpec.Builder initMethod = MethodSpec.methodBuilder("init").addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                .addStatement("System.out.println($S)","init router,23333");
        for (Element element : routerList){
            Router router = element.getAnnotation(Router.class);
            ClassName className = null;
            if (element.getKind() == ElementKind.CLASS){
                System.out.println("liwy class=====");
                className =  ClassName.get((TypeElement) element);
                initMethod.addStatement("com.liwy.easyrouter.router.Routers.map($S,$T.class)",router.value(),className);
            }else if (element.getKind() == ElementKind.METHOD){
                className = ClassName.get((TypeElement) element.getEnclosingElement());
                System.out.println("liwy method====" + element.getSimpleName() + ",className=" + className.simpleName());
            }
            //create RouterInit
            TypeSpec routerInit = TypeSpec.classBuilder("RouterInit")
                    .addModifiers(Modifier.PUBLIC,Modifier.FINAL)
                    .addMethod(initMethod.build())
                    .build();

            try {
                JavaFile.builder("com.liwy.easyrouter.router",routerInit).build().writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("liwy router.value=" + router.value() + ",router.class=" + className.getClass().getName() + ",router.name=" +className.simpleName());
        }
    }
}
