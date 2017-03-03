package it.discovery.marina;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static it.discovery.marina.FunctionStyleDemo2.combine;
import static it.discovery.marina.FunctionStyleDemo2.getStream;

public class ReflectionDemo
{

    public static void main(String[] args)
    {
        printSignatures(getCommonMethods(Method.class, ArrayList.class));
        System.out.println("========");
        printSignatures(getCommonMethods(List.class, Set.class));
        System.out.println("========");
        System.out.println(getCommonMethods((Class)null, (Class)null));

        System.out.println(getAllMethods(null));
        System.out.println(getAllMethods(Object.class));
        System.out.println(getAllMethods(int.class));

        System.out.println("Nulls:" + getCommonMethods((List<Method>)null, (List<Method>)null));
        getCommonMethods(new ArrayList<>(), new ArrayList<>());
        areMethodsEqual(null, null);
        printSignatures(null);
        printSignature(null);
        removeDuplicates(null);
        getFirstDuplicate(null);
        getMethodsCount(null, null);

        System.out.println(getNonAbstractSuperclasses(ArrayList.class));
        System.out.println(getNonAbstractSuperclasses(List.class));
        System.out.println(getNonAbstractSuperclasses(null));
        System.out.println(getNonAbstractSuperclasses(HashMap.class));

        getInterfaces(null);
        getInterfaces(HashMap.class).forEach(System.out::println);


    }

    public static List<Class> getNonAbstractSuperclasses(Class class1)
    {
        List<Class> list = new ArrayList<>();
        while(class1 != null)
        {
            list = combine(list, getInterfaces(class1).collect(Collectors.toList()));
            class1 = class1.getSuperclass();
            if(Optional.ofNullable(class1).filter((n)->!Modifier.isAbstract(n.getModifiers())).isPresent())
            {
                list.add(class1);
            }
        }
        return list;
    }

    public static Stream<Class> getInterfaces(Class class1)
    {
        if(class1 == null)
        {
            return Stream.empty();
        }
        Class[] interfaces = class1.getInterfaces();
        return Stream.concat(Arrays.stream(interfaces), Arrays.stream(interfaces).flatMap(ReflectionDemo::getInterfaces));
    }

    public static List<Method> getCommonMethods(Class class1, Class class2)
    {
        return getCommonMethods(getAllMethods(class1), getAllMethods(class2));

    }
    public static List<Method> getAllMethods(Class class1)
    {
        if(class1 == null)
        {
            return new ArrayList<>();
        }
        return combine(Arrays.asList(class1.getDeclaredMethods()),
                getAllMethods(class1.getSuperclass()));
    }

    public static List<Method> getCommonMethods(List<Method>list1, List<Method>list2)
    {
        List<Method> commonMethods = getStream(list1).filter( (n) -> getMethodsCount(n, list2) > 0 ).collect(Collectors.toList());
        removeDuplicates(commonMethods);

        return commonMethods;
    }

    public static long getMethodsCount(Method method, List<Method> list)
    {
        return getStream(list).filter((n)->areMethodsEqual(method, n)).count();
     }

    public static boolean areMethodsEqual(Method method1, Method method2)
    {
        return  method1!=null &&
                method2!= null &&
                method1.getName().equals(method2.getName()) &&
                method1.getReturnType().equals(method2.getReturnType()) &&
                Arrays.equals(method1.getParameterTypes(), method2.getParameterTypes());
    }



    public static void removeDuplicates(List<Method> list)
    {
        Optional<Method> m = getFirstDuplicate(list);
        while(m.isPresent())
        {
            list.remove(m.get());
            m = getFirstDuplicate(list);
        }
   }

    public static Optional<Method> getFirstDuplicate(List<Method> list)
    {
        return getStream(list).filter((n) -> getMethodsCount(n, list) > 1).findFirst();
    }

    public static void printSignatures(List<Method> methods)
    {
        getStream(methods).forEach(ReflectionDemo::printSignature);
    }
    public static void printSignature(Method method)
    {
        Optional.ofNullable(method).map( (m) ->
        {
            System.out.print(m.getReturnType().getSimpleName()+" ");
            System.out.print(m.getName()+"(");
            Class<?>[] params = m.getParameterTypes();

            for(int i = 0; i<params.length; i++)
            {
                System.out.print(params[i].getSimpleName());
                if(i + 1 <params.length)
                {
                    System.out.print(",");
                }
            }

            System.out.println(");");
            return true;
        });
    }
}
