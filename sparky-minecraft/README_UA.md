# Адаптер SparkyLib для Minecraft

## Огляд
Адаптер SparkyLib для Minecraft - це спеціалізована бібліотека, яка розширює основний фреймворк SparkyLib для забезпечення безшовної інтеграції з серверами Minecraft (Bukkit/Spigot). Він використовує архітектуру Entity-Component-System (ECS) для створення потужного та гнучкого фреймворку для розробки плагінів Minecraft.

## Можливості

### Компоненти ECS для Minecraft
- **BlockComponent** - Представляє блоки Minecraft з позицією, типом та інформацією про світ
- **PlayerComponent** - Управляє станом гравця, включаючи здоров'я, рівень їжі, досвід та статус онлайн
- **ItemComponent** - Обробляє предмети Minecraft з типом, кількістю, міцністю, відображуваним ім'ям та описом

### Системи ECS для Minecraft
- **BlockSystem** - Управляє маніпуляціями з блоками та взаємодією зі світом
- **PlayerSystem** - Обробляє оновлення стану гравців та управління ними
- **ItemSystem** - Управляє взаємодією з предметами та операціями інвентарю

### Функції інтеграції
- Безшовна інтеграція з API Bukkit/Spigot
- Архітектура на основі подій з використанням EventBus SparkyLib
- Інтеграція планувальника для операцій по часу
- Система ефектів частинок
- Обробка команд з дозволами
- Управління активами для ресурсів

## Встановлення

Додайте наступну залежність до вашого `pom.xml`:

```xml
<dependency>
    <groupId>com.sparky</groupId>
    <artifactId>sparky-minecraft</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Приклад використання

```java
// Ініціалізація адаптера Minecraft
MinecraftAdapter minecraftAdapter = new MinecraftAdapter(yourPlugin);

// Створення сутності гравця
Entity playerEntity = minecraftAdapter.getEntityManager().createEntity();
PlayerComponent playerComponent = new PlayerComponent(
    "Steve", 
    "player-uuid", 
    20, 20, 15, 10, 0.5f, true
);
playerEntity.addComponent(playerComponent);

// Створення сутності блоку
Entity blockEntity = minecraftAdapter.getEntityManager().createEntity();
BlockComponent blockComponent = new BlockComponent(
    "diamond_ore", 
    50, 64, 50, 
    "world"
);
blockEntity.addComponent(blockComponent);

// Системи автоматично оброблять ці сутності
minecraftAdapter.update();
```

## Архітектура

Адаптер Minecraft слідує тій самій архітектурі ECS, що й основний SparkyLib:

1. **Сутності** - Ігрові об'єкти, які містять компоненти
2. **Компоненти** - Контейнери даних, що описують аспекти сутності
3. **Системи** - Процесори логіки, які працюють з сутностями з певними компонентами

Ця архітектура забезпечує:
- Високу продуктивність завдяки орієнтації на дані
- Легку розширюваність шляхом додавання нових компонентів та систем
- Чіткий розподіл відповідальності
- Гнучку композицію сутностей

## Вимоги
- Java 17 або вище
- Bukkit/Spigot 1.19.4 або сумісний
- Основні модулі SparkyLib

## Ліцензія
Цей проект ліцензований за ліцензією MIT - дивіться файл LICENSE для отримання детальної інформації.