import java.util.*;
import java.util.stream.*;

public class Main {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        // Находим количество несовершеннолетних (т.е. людей младше 18 лет)
        long count = persons.stream()
                .filter(age -> age.getAge() < 18)
                .count();
        System.out.println("Количество несовершеннолетних (т.е. людей младше 18 лет) = " + count + " чел.");

        // Получаем список фамилий призывников (т.е. мужчин от 18 и до 27 лет).
        List<String> family = persons.stream()
                .filter(age -> age.getAge() >= 18 && age.getAge() < 27)
                .limit(50) // Ограничимся списком в 50 человек
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println("Список фамилий призывников (т.е. мужчин от 18 и до 27 лет):");
        System.out.println(family);

        // Получаем отсортированный по фамилии список
        // потенциально работоспособных людей с высшим образованием
        // (т.е. с высшим образованием в возрасте от 18 до 60 лет для женщин и до 65 лет для мужчин)
        List<Person> selection = persons.stream()
                .filter(education -> education.getEducation().equals(Education.HIGHER))
                .filter(age -> age.getAge() >= 18)
                .filter(x -> (x.getSex().equals(Sex.MAN)) ? (x.getAge() < 65) : (x.getAge() < 60))
                .limit(10) // Ограничимся списком в 10 человек
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        System.out.println("Список фамилий потенциально работоспособных людей с высшим образованием\n" +
                "(т.е. с высшим образованием в возрасте от 18 до 60 лет для женщин и до 65 лет для мужчин):");
        System.out.println(selection);
    }
}
