# Дизайн-документ: Тематические лут-контейнеры («Находки из заброшек»)
**Модпак:** Nuclear Tech New Horizons (NTNH)  
**Мод:** Enhanced LootBags 1.3.4 (YAMCore)  
**Файл конфигурации:** `config/EnhancedLootBags/LootBags.xml`  
**Интеграция в структуры:** `config/hbmConfig/_hbmItemPools.json`  
**Локализация:** `config/txloader/forceload/enhancedlootbags/lang/ru_RU.lang`  
**Статус:** Утверждённая спецификация после независимого аудита и арбитража

---

## 1. Философия геймдизайна

1. **Никакого абстрактного мусора:**
   Каждый контейнер — это аутентичная личная вещь, ЗИП или инструментальная сумка (*«Сумка электрика»*, *«Патронный цинк»*, *«Чёрный ящик шаттла»*). Вторичный лут знакомит игрока с механиками модов сборки (ModernMarkings, BiblioCraft, Storage Drawers, Computronics).

2. **Практическая ценность:**
   Лут либо решает насущные задачи выживания (патроны, чистая вода, бинты, фильтры противогазов), либо существенно экономит время и ресурсы крафта (мультиметры, геосканеры, сортовые семена).

3. **Трёхступенчатая шкала сохранности и объёма дропа:**
   * 🥉 **Tier 1: Потрёпанный / Вскрытый** (`1–2 предмета`, расходники — до `2–3`). Найден на поверхности и в открытых заброшках. Содержит умеренную примесь мусора группы 0 (`CombineTrashGroup="true"`). Механика зачарования на Удачу отключена (`AllowFortuneBags=false`) для сохранения баланса.
   * 🥈 **Tier 2: Штатный / Производственный** (`2–3 предмета`, расширенный — до `2–4`). Шкафчики заводских цехов, бытовки, лаборатории. Профильный инструмент, приборы и качественные расходники (`CombineTrashGroup="true"`).
   * 🥇 **Tier 3: Опечатанный / Военный / Заводской** (`2–5 предметов`, либо целевой оптовый стек). Сейфы бункеров NTM, закрытые лаборатории, место крушения челнока. Высокотехнологичное оборудование и чистые материалы без примеси хлама (`CombineTrashGroup="false"`).

4. **Механика группировки лута (`ItemGroup`):**
   В моде Enhanced LootBags атрибут `ItemGroup="имя_группы"` связывает связанные предметы в неделимый бандл. При выпадении позиции с данным тегом игрок гарантированно получает весь комплект (например, калибр патронов вместе с гильзами), что считается за один ролл из общего лимита `MinItems`–`MaxItems`.

5. **Модульная архитектура с запасом (шаг в 30 ID):**
   Каждая специализация занимает диапазон из 30 номеров (10–39, 40–69 и т.д.). Первые номера отданы под базовые тиры (Tier 1–3), а остальные зарезервированы под узкопрофильные расширения (моно-лутбеки конкретных калибров, специализированные ЗИП, химреактивы).

6. **«Маяки прогресса» (Curiosity Hooks):**
   Редкие находки компонентов старших технологических эпох с низким шансом (3–8% на ролл, строго по 1 шт., без возможности немедленного использования без инфраструктуры). Подробное геймдизайнерское обоснование приведено в **разделе 7**.

---

## 2. Сетка специализаций (Диапазоны ID)

```
[0]        Мусорная группа (примешивается к Tier 1 и 2)
[10–39]    Медицина, СИЗ и фармакология (10: Рваная аптечка, 11: Цеховая аптечка, 12: Кейс РХБЗ)
           └ Резерв 13–39: сыворотки, антирады, хирургия, спец-СИЗ биозащиты
[40–69]    Слесарка, механика и взлом (40: Ящик слесаря, 41: Сумка механика, 42: Инженерный ЗИП)
           └ Резерв 43–69: прецизионный инструмент, гидравлика, взлом сейфов высокого уровня
[70–99]    Электрика, КИПиА и автоматика (70: Скрутка проводов, 71: Сумка электрика, 72: Потрёпанный блок стойки ЭВМ)
           └ Резерв 73–99: компоненты высоковольтных подстанций, логические платы, датчики РБМК
[100–129]  Оружие, патроны и обвесы (100: Вскрытый патронный цинк, 101: Запечатанный патронный цинк, 102: Оружейный кофр)
           └ Резерв 103–129: моно-подсумки калибров (.22 LR, .45 ACP, 12ga, 7.62x54R), выстрелы РПГ
[130–159]  Кулинария, провизия и быт (130: Котелок бродяги, 131: Походный кухонник, 132: Офицерский паёк)
           └ Резерв 133–159: специи, консервированные рационы экспедиций, полевая стерилизация
[160–189]  Агрономия, оранжерея и банк семян (160: Семена с огорода, 161: Посевной мешок, 162: Банк семян)
           └ Резерв 163–189: гидропонные культуры, стимуляторы роста, мутагенные саженцы
[190–219]  Строительство, отделка и чертежи (190: Мешок плотника, 191: Сумка маляра, 192: Кейс проектировщика)
           └ Резерв 193–219: трафареты, архитектурные резцы, монолитная опалубка
[220–249]  Геологоразведка и горное дело (220: Планшет разведчика, 221: Геодезический набор, 222: Спектрометр недр)
           └ Резерв 223–249: глубинное бурение, маркшейдерские метки, керны пород
[250–279]  Связь, радио и сигнализация (250: Сумка сигнальщика, 251: Коробочка радиодеталей, 252: Радиоузел связиста)
           └ Резерв 253–279: шифровальные таблицы, полевые радиостанции, радарные маяки
[280–309]  Химия, полимеры и лабораторная посуда (280: Сумка лаборанта, 281: Кейс технолога)
           └ Резерв 282–309: катализаторы крекинга, пирофорные реагенты, кислотостойкая тара
[310–339]  Астронавтика и упавший челнок (310: Чёрный ящик шаттла, 311: Транспортный пенал изотопов, 312: Бортовой ремнабор)
           └ Резерв 313–339: термозащитные плитки, авионика маневровых двигателей, скафандры
[340+]     Резерв под будущие расширения
```

---

## 3. Детальный состав контейнеров

> Все названия предметов, метаданные и имена реестров строго выверены по конфигурационному файлу `LootBags.xml`, `.lang` файлам и коду сборки.

---

### Группа 0: Мусор заброшек (`Trash`)
* **ID:** `0` | **Rarity:** `0` | **Дроп:** `1` | `CombineTrashGroup="false"`
* **Содержимое:**
  * `hbm:item.rag` (**Тряпка**) | Кол-во: 1–3 | Вес: 100 | `RandomAmount="true"`. *Смысл:* Ветошь для факелов, перевязок и фильтров.
  * `hbm:item.dust_tiny` (**Кучка пыли**) | Кол-во: 2–6 | Вес: 80 | `RandomAmount="true"`. *Смысл:* Промышленная металлическая пыль.
  * `exnihilo:stone` (**Камень**) | Кол-во: 2–6 | Вес: 80 | `RandomAmount="true"`. *Смысл:* Мелкая галька (собирается в булыжник).
  * `minecraft:stick` (**Палка**) | Кол-во: 1–4 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.debris_metal` (**Покорёженная металлическая балка**) | Кол-во: 1 | Вес: 50 | `RandomAmount="false"`. *Смысл:* Переплавляется в железо.
  * `hbm:item.mask_piss` (**Траншейный противогаз**) | Кол-во: 1 | Вес: 15 | `RandomAmount="false"`. *Смысл:* Примитивная тканевая влажная маска.

---

### Блок 10–39: Медицина, СИЗ и фармакология

#### 🩹 [Meta 10] Рваная аптечка
* **Уровень:** 1 | **Rarity:** 0 | **Дроп:** `1 - 2` | `CombineTrashGroup="true"`
* *Лор:* Полупустой санитарный пакет с поста охраны или из жилого сектора.
* **Содержимое:**
  * `hbm:item.pill_iodine` (**Таблетка йода**) | Кол-во: 2–4 | Вес: 100 | `RandomAmount="true"`. *Смысл:* Снятие накопленной радиации.
  * `harvestcraft:wovencottonItem` (**Тканный хлопок**) | Кол-во: 2–4 | Вес: 90 | `RandomAmount="true"`. *Смысл:* Чистый перевязочный материал.
  * `hbm:item.plant_item:2` (**Лист горчичной ивы**) | Кол-во: 1–3 | Вес: 80 | `RandomAmount="true"`. *Смысл:* Лекарственное сырьё NTM.
  * `hbm:item.syringe_antidote` (**Антидот**) | Кол-во: 1 | Вес: 60 | `RandomAmount="false"`. *Смысл:* Нейтрализация ядов и химического отравления.
  * `hbm:item.syringe_empty` (**Пустой шприц**) | Кол-во: 1–2 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.mask_rag` (**Грубая защитная маска**) | Кол-во: 1 | Вес: 40 | `RandomAmount="false"`. *Смысл:* Стартовый респиратор от пыли.
  * `hbm:item.mask_piss` (**Траншейный противогаз**) | Кол-во: 1 | Вес: 30 | `RandomAmount="false"`
  * `hbm:item.syringe_metal_stimpak` (**Стимулятор**) | Кол-во: 1 | Вес: 25 | `RandomAmount="false"`. *Смысл:* Экстренное лечение.
  * `hbm:item.rag` (**Тряпка**) | Кол-во: 2–4 | Вес: 90 | `RandomAmount="true"`
  * `hbm:item.pill_herbal` (**Травяная таблетка**) | Кол-во: 1–2 | Вес: 70 | `RandomAmount="true"`
  * `harvestcraft:saltItem` (**Соль**) | Кол-во: 3–6 | Вес: 80 | `RandomAmount="true"`
  * `cfm:ItemSoap` (**Мыло**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `minecraft:glass_bottle` (**Колба**) | Кол-во: 1–2 | Вес: 65 | `RandomAmount="true"`
  * `minecraft:potion` (**Бутылочка воды**) | Кол-во: 1 | Вес: 70 | `RandomAmount="false"`
  * `minecraft:string` (**Нить**) | Кол-во: 1–3 | Вес: 75 | `RandomAmount="true"`
  * `minecraft:sugar` (**Сахар**) | Кол-во: 2–4 | Вес: 60 | `RandomAmount="true"`
  * `minecraft:bowl` (**Миска**) | Кол-во: 1 | Вес: 50 | `RandomAmount="false"`
  * `hbm:item.powder_coal` (**Угольный порошок**) | Кол-во: 3–6 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.powder_calcium` (**Карбонат кальция**) | Кол-во: 2–4 | Вес: 55 | `RandomAmount="true"`
  * `hbm:item.plastic_bag` (**Пакет**) | Кол-во: 1–2 | Вес: 60 | `RandomAmount="true"`

#### 🩺 [Meta 11] Цеховая аптечка
* **Уровень:** 2 | **Rarity:** 1 | **Дроп:** `2 - 3` | `CombineTrashGroup="true"`
* *Лор:* Настенная металлическая аптечка первой помощи производственного участка.
* **Содержимое:**
  * `hbm:item.med_bag` (**Аптечка первой помощи**) | Кол-во: 1 | Вес: 60 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Саквояж врача (лечение переломов и контузий).
  * `hbm:item.syringe_metal_stimpak` (**Стимулятор**) | Кол-во: 1–2 | Вес: 80 | `RandomAmount="true"`
  * `hbm:item.syringe_metal_medx` (**Мед-X**) | Кол-во: 1 | Вес: 60 | `RandomAmount="false"`. *Смысл:* Обезболивающее, снижает входящий урон.
  * `hbm:item.iv_blood` (**Пакет с кровью**) | Кол-во: 1 | Вес: 50 | `RandomAmount="false"`. *Смысл:* Восстановление при критической кровопотере.
  * `hbm:item.iv_empty` (**Пакет для капельниц**) | Кол-во: 1–2 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.gas_mask_olde` (**Кожаный противогаз**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.goggles` (**Защитные очки**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Защита глаз от ослепления и вспышек сварки.
  * `hbm:item.radx` (**Рад-X**) | Кол-во: 1–2 | Вес: 45 | `RandomAmount="true"`. *Смысл:* Временная стойкость к радиации.
  * `hbm:item.pill_iodine` (**Таблетка йода**) | Кол-во: 4–8 | Вес: 70 | `RandomAmount="true"`
  * `hbm:item.filter_coal` (**Угольный фильтр**) | Кол-во: 1–2 | Вес: 60 | `RandomAmount="true"`. *Смысл:* Сменный картридж противогаза.
  * `hbm:item.gas_mask_mono` (**Полумаска**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.syringe_metal_empty` (**Металлический шприц**) | Кол-во: 1 | Вес: 50 | `RandomAmount="false"`
  * `hbm:item.syringe_antidote` (**Антидот**) | Кол-во: 1 | Вес: 55 | `RandomAmount="false"`
  * `hbm:item.syringe_metal_super` (**Супер-стимулятор**) | Кол-во: 1 | Вес: 20 | `RandomAmount="false"`
  * `hbm:item.bottle_mercury` (**Пузырёк ртути**) | Кол-во: 1 | Вес: 40 | `RandomAmount="false"`
  * `hbm:item.pipette_laboratory` (**Лабораторная пипетка**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.pads_rubber` (**Резиновые прокладки**) | Кол-во: 2–3 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.hazmat_cloth` (**Прорезиненная ткань**) | Кол-во: 1–2 | Вес: 45 | `RandomAmount="true"`
  * `harvestcraft:wovencottonItem` (**Тканный хлопок**) | Кол-во: 3–6 | Вес: 75 | `RandomAmount="true"`
  * `hbm:item.syringe_awesome` (**Чудо-сыворотка**) | Кол-во: 1 | Вес: 15 | `RandomAmount="false"`

#### 💉 [Meta 12] Кейс РХБЗ
* **Уровень:** 3 | **Rarity:** 2 | **Дроп:** `3 - 5` | `CombineTrashGroup="false"`
* *Лор:* Герметичный армейский чемодан службы радиационной и химической разведки из защищённого бункера.
* **Содержимое:**
  * `hbm:item.gas_mask_m65` (**Противогаз M65-Z**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Полная фильтрация от радиоактивной взвеси и газов.
  * `hbm:item.gas_mask_mono` (**Полумаска**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.geiger_counter` (**Счётчик Гейгера**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Замер текущего радиационного фона.
  * `hbm:item.dosimeter` (**Дозиметр**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Замер накопленной дозы RAD.
  * `hbm:item.pollution_detector` (**Детектор загрязнения**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.atmosphere_scanner` (**Анализатор атмосферы**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.digamma_diagnostic` (**Диагностика дигаммы**) | Кол-во: 1 | Вес: 25 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Обнаружение DRX-излучения.
  * `hbm:item.hazmat_helmet` (**Шлем костюма химзащиты**) | Кол-во: 1 | Вес: 30 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.syringe_metal_super` (**Супер-стимулятор**) | Кол-во: 1 | Вес: 35 | `RandomAmount="false"`
  * `hbm:item.syringe_metal_psycho` (**Психо**) | Кол-во: 1 | Вес: 30 | `RandomAmount="false"`. *Смысл:* Боевой стимулятор (+урон).
  * `hbm:item.syringe_metal_medx` (**Мед-X**) | Кол-во: 1–2 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.radx` (**Рад-X**) | Кол-во: 2–4 | Вес: 55 | `RandomAmount="true"`
  * `hbm:item.pill_red` (**Красная таблетка**) | Кол-во: 1 | Вес: 15 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.hazmat_cloth_grey` (**Серая ткань химзащиты**) | Кол-во: 2–3 | Вес: 40 | `RandomAmount="true"`
  * `hbm:item.hazmat_cloth_red` (**Красная ткань химзащиты**) | Кол-во: 1–2 | Вес: 30 | `RandomAmount="true"`
  * `hbm:item.pipette_boron` (**Борная пипетка**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Кислотостойкая пипетка на 1000 mB.
  * `hbm:item.plate_lead` (**Свинцовая пластина**) | Кол-во: 2–4 | Вес: 50 | `RandomAmount="true"`. *Смысл:* Радиационная экранировка.
  * `hbm:item.powder_lead` (**Свинцовый порошок**) | Кол-во: 3–6 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.reacher` (**Вольфрамовые хваталки**) | Кол-во: 1 | Вес: 25 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Безопасная переноска радиоактивных материалов.
  * `hbm:item.cell_deuterium` (**Пробирка с дейтерием**) | Кол-во: 1 | Вес: 15 | `RandomAmount="false"`

---

### Блок 40–69: Слесарка, механизмы и взлом

#### 🧰 [Meta 40] Ящик слесаря
* **Уровень:** 1 | **Rarity:** 0 | **Дроп:** `1 - 2` | `CombineTrashGroup="true"`
* *Лор:* Переносной слесарный ящик для сантехнических работ и взлома дверей.
* **Содержимое:**
  * `hbm:item.pin` (**Отмычка**) | Кол-во: 3–6 | Вес: 100 | `RandomAmount="true"`. *Смысл:* Взлом замков дверей и сейфов NTM.
  * `OpenBlocks:wrench` (**Big Metal Bar**) | Кол-во: 1 | Вес: 50 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Монтировка-ключ OpenBlocks.
  * `hbm:item.pads_rubber` (**Резиновые прокладки**) | Кол-во: 2–4 | Вес: 90 | `RandomAmount="true"`
  * `hbm:item.padlock_rusty` (**Ржавый замок**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="2"` | `RandomAmount="false"`
  * `hbm:item.key` (**Заготовка ключа**) | Кол-во: 1 | Вес: 40 | `RandomAmount="false"`
  * `hbm:item.plastic_bag` (**Пластиковый пакет**) | Кол-во: 1–2 | Вес: 70 | `RandomAmount="true"`
  * `hbm:item.screwdriver` (**Шлицевая отвёртка**) | Кол-во: 1 | Вес: 50 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.wrench` (**Трубный ключ**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.saw_iron` (**Ножовка по металлу**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `minecraft:iron_ingot` (**Железный слиток**) | Кол-во: 1–3 | Вес: 70 | `RandomAmount="true"`
  * `hbm:item.plate_iron` (**Железная пластина**) | Кол-во: 1–3 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.powder_iron` (**Железный порошок**) | Кол-во: 3–6 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.debris_metal` (**Металлолом**) | Кол-во: 1–2 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.debris_shrapnel` (**Шрапнель**) | Кол-во: 4–8 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.casing:0` (**Малая бронзовая гильза**) | Кол-во: 3–6 | Вес: 55 | `RandomAmount="true"`
  * `minecraft:flint` (**Кремень**) | Кол-во: 1–3 | Вес: 65 | `RandomAmount="true"`
  * `minecraft:lever` (**Рычаг**) | Кол-во: 1–2 | Вес: 50 | `RandomAmount="true"`
  * `minecraft:tripwire_hook` (**Натяжной крюк**) | Кол-во: 1–2 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.wire_fine:2900` (**Медная проволока**) | Кол-во: 3–6 | Вес: 75 | `RandomAmount="true"`

#### 🔧 [Meta 41] Сумка механика
* **Уровень:** 2 | **Rarity:** 1 | **Дроп:** `2 - 3` | `CombineTrashGroup="true"`
* *Лор:* Кожаная инструментальная сумка дежурного слесаря машинного отделения.
* **Содержимое:**
  * `hbm:item.wd40` (**VT-40**) | Кол-во: 1 | Вес: 80 | `LimitedDropCount="2"` | `RandomAmount="false"`. *Смысл:* Проникающая смазка NTM.
  * `OpenBlocks:spongeonastick` (**Губка на палочке**) | Кол-во: 1 | Вес: 50 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Аварийный сбор разливов жидкостей и кислот.
  * `hbm:item.padlock` (**Стальной замок**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="2"` | `RandomAmount="false"`
  * `hbm:item.key_kit` (**Слепки для ключей**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.key_fake` (**Поддельный ключ**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.mirror_tool` (**Юстировочный ключ зеркал**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `vending:vendingMachineWrench` (**Ключ обменного блока**) | Кол-во: 1 | Вес: 30 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.plate_steel` (**Стальная пластина**) | Кол-во: 2–4 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.pipe:1300` (**Алюминиевая труба**) | Кол-во: 4–8 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.screwdriver_steel` (**Стальная отвёртка**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.drillbit:0` (**Стальное сверло**) | Кол-во: 1 | Вес: 50 | `RandomAmount="false"`
  * `hbm:item.blades_steel` (**Стальные лезвия шредера**) | Кол-во: 1 | Вес: 40 | `RandomAmount="false"`
  * `hbm:item.pipes_steel` (**Стальные трубы**) | Кол-во: 1 | Вес: 35 | `RandomAmount="false"`
  * `hbm:item.ingot_steel` (**Стальной слиток**) | Кол-во: 3–6 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.plate_copper` (**Медная пластина**) | Кол-во: 2–4 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.plate_aluminium` (**Алюминиевая пластина**) | Кол-во: 2–4 | Вес: 55 | `RandomAmount="true"`
  * `hbm:item.wire_fine:8` (**Алюминиевый провод**) | Кол-во: 3–6 | Вес: 55 | `RandomAmount="true"`
  * `hbm:item.ball_resin` (**Смола / Латекс**) | Кол-во: 1–3 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.pads_rubber` (**Резиновые прокладки**) | Кол-во: 2–4 | Вес: 70 | `RandomAmount="true"`
  * `hbm:item.motor_desh` (**Деш-мотор**) | Кол-во: 1 | Вес: 15 | `RandomAmount="false"`

#### ⚙️ [Meta 42] Инженерный ЗИП
* **Уровень:** 3 | **Rarity:** 2 | **Дроп:** `3 - 4` | `CombineTrashGroup="false"`
* *Лор:* Опечатанный ящик ЗИП из кабинета главного инженера обогатительного комбината.
* **Содержимое:**
  * `hbm:item.wrench_archineer` (**Гаечный ключ инженера**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Монтаж тяжёлых многоблоков NTM.
  * `hbm:item.reacher` (**Вольфрамовые хваталки**) | Кол-во: 1 | Вес: 30 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.padlock_reinforced` (**Защищённый замок**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.drill_titanium` (**Титановый бур**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.motor_desh` (**Деш-мотор**) | Кол-во: 1–2 | Вес: 35 | `RandomAmount="true"`
  * `hbm:item.screwdriver_desh` (**Деш-отвёртка**) | Кол-во: 1 | Вес: 30 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.drillbit:2` (**HSS-сверло**) | Кол-во: 1 | Вес: 35 | `RandomAmount="false"`
  * `hbm:item.drillbit:1` (**Алмазное сверло**) | Кол-во: 1 | Вес: 30 | `RandomAmount="false"`
  * `hbm:item.blade_titanium` (**Титановые лезвия**) | Кол-во: 1 | Вес: 35 | `RandomAmount="false"`
  * `hbm:item.blade_tungsten` (**Вольфрамовые лезвия**) | Кол-во: 1 | Вес: 30 | `RandomAmount="false"`
  * `hbm:item.plate_armor_titanium` (**Титановая бронепластина**) | Кол-во: 1–3 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.plate_dura_steel` (**Пластина дюрастали**) | Кол-во: 2–4 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.plate_mixed` (**Пластина улучшенного сплава**) | Кол-во: 1–2 | Вес: 40 | `RandomAmount="true"`
  * `hbm:item.ingot_tungsten` (**Вольфрамовый слиток**) | Кол-во: 2–4 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.ingot_titanium` (**Титановый слиток**) | Кол-во: 2–4 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.powder_titanium` (**Титановый порошок**) | Кол-во: 3–6 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.hard_drive` (**Жёсткий диск**) | Кол-во: 1 | Вес: 30 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.industrial_magnet` (**Промышленный магнит**) | Кол-во: 1 | Вес: 25 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.catalytic_converter` (**Каталитический нейтрализатор**) | Кол-во: 1 | Вес: 25 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.cladding_desh` (**Деш-оболочка**) | Кол-во: 1–2 | Вес: 30 | `RandomAmount="true"`

---

### Блок 70–99: Электрика, КИПиА и автоматика

#### 🔌 [Meta 70] Скрутка проводов
* **Уровень:** 1 | **Rarity:** 0 | **Дроп:** `2 - 3` | `CombineTrashGroup="true"`
* *Лор:* Срезанные со щитков остатки медной проводки, радиодеталей и кристаллов.
* **Содержимое:**
  * `hbm:item.wire_fine:2900` (**Тонкий медный провод**) | Кол-во: 6–12 | Вес: 100 | `RandomAmount="true"`
  * `hbm:tile.red_cable_classic` (**Медный силовой кабель**) | Кол-во: 4–8 | Вес: 80 | `RandomAmount="true"`. *Смысл:* Базовый кабель NTM (ItemBlock).
  * `minecraft:quartz` (**Кварц**) | Кол-во: 3–6 | Вес: 80 | `RandomAmount="true"`. *Смысл:* Пьезокристаллы для схем и резонаторов.
  * `minecraft:redstone` (**Красная пыль**) | Кол-во: 4–8 | Вес: 80 | `RandomAmount="true"`
  * `hbm:item.battery_potato` (**Картофельная батарейка**) | Кол-во: 1 | Вес: 20 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.battery_pack:0` (**Батарейка**) | Кол-во: 1 | Вес: 45 | `RandomAmount="false"`
  * `minecraft:repeater` (**Повторитель**) | Кол-во: 1–2 | Вес: 60 | `RandomAmount="true"`
  * `minecraft:comparator` (**Компаратор**) | Кол-во: 1–2 | Вес: 50 | `RandomAmount="true"`
  * `minecraft:redstone_torch` (**Красный факел**) | Кол-во: 2–4 | Вес: 70 | `RandomAmount="true"`
  * `minecraft:glowstone_dust` (**Светопыль**) | Кол-во: 3–6 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.circuit:0` (**Вакуумная лампа**) | Кол-во: 1–3 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.circuit:1` (**Конденсатор**) | Кол-во: 1–3 | Вес: 65 | `RandomAmount="true"`
  * `ProjRed|Core:projectred.core.part:16` (**Медная катушка**) | Кол-во: 1–3 | Вес: 60 | `RandomAmount="true"`
  * `ProjRed|Core:projectred.core.part:17` (**Ферритовый сердечник**) | Кол-во: 1–3 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.wire_dense:2900` (**Плотный медный провод**) | Кол-во: 3–6 | Вес: 70 | `RandomAmount="true"`
  * `hbm:item.powder_copper` (**Медный порошок**) | Кол-во: 3–6 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.plate_copper` (**Медная пластина**) | Кол-во: 1–3 | Вес: 60 | `RandomAmount="true"`
  * `minecraft:iron_ingot` (**Железный слиток**) | Кол-во: 1–3 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.debris_metal` (**Металлолом**) | Кол-во: 1–2 | Вес: 55 | `RandomAmount="true"`

#### ⚡ [Meta 71] Сумка электрика
* **Уровень:** 2 | **Rarity:** 1 | **Дроп:** `2 - 4` | `CombineTrashGroup="true"`
* *Лор:* Рабочий набор наладчика контрольно-измерительных приборов и автоматики (КИПиА).
* **Содержимое:**
  * `hbm:item.power_net_tool` (**Анализатор электросети**) | Кол-во: 1 | Вес: 55 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Замер напряжения и сопротивления кабельной сети.
  * `hbm:item.analysis_tool` (**Анализатор**) | Кол-во: 1 | Вес: 50 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Диагностика состояния механизмов и труб NTM.
  * `hbm:item.settings_tool` (**Устройство настройки**) | Кол-во: 1 | Вес: 50 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Конфигуратор сторон машин.
  * `hbm:item.reactor_sensor` (**Датчик реактора**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:tile.red_cable_box:4` (**Коробчатый кабель**) | Кол-во: 4–8 | Вес: 65 | `RandomAmount="true"`
  * `ProjRed|Core:projectred.core.screwdriver` (**Отвёртка**) | Кол-во: 1 | Вес: 50 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `ProjRed|Core:projectred.core.wiredebugger` (**Отладчик проводов**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `ProjRed|Core:projectred.core.part:16` (**Индукционная катушка**) | Кол-во: 2–4 | Вес: 50 | `RandomAmount="true"`
  * `ProjRed|Core:projectred.core.part:17` (**Ферритовый сердечник**) | Кол-во: 2–4 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.circuit:0` (**Вакуумная лампа**) | Кол-во: 2–4 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.circuit:1` (**Конденсатор**) | Кол-во: 2–4 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.circuit:2` (**Аналоговая плата**) | Кол-во: 1–2 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.circuit:3` (**Цифровая плата**) | Кол-во: 1–2 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.wire_fine:2900` (**Медный провод**) | Кол-во: 3–6 | Вес: 55 | `RandomAmount="true"`
  * `hbm:item.wire_fine:7900` (**Золотой провод**) | Кол-во: 2–4 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.battery_pack:2` (**Улучшенная батарея**) | Кол-во: 1 | Вес: 40 | `RandomAmount="false"`
  * `hbm:item.billet_silicon` (**Кремниевая заготовка**) | Кол-во: 3–6 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.photo_panel` (**Фотоэлемент**) | Кол-во: 1–2 | Вес: 35 | `RandomAmount="true"`
  * `hbm:item.magnetron` (**Магнетрон**) | Кол-во: 1 | Вес: 30 | `RandomAmount="false"`
  * `hbm:item.crt_display` (**ЭЛТ-дисплей**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.circuit:10` (**Военная микросхема**) | Кол-во: 1 | Вес: 15 | `RandomAmount="false"`

#### 🖥️ [Meta 72] Потрёпанный блок стойки ЭВМ
* **Уровень:** 3 | **Rarity:** 2 | **Дроп:** `3 - 5` | `CombineTrashGroup="false"`
* *Лор:* Извлечённый ломом и частично раскуроченный вычислительный блок из серверной стойки промышленной АСУ ТП. Внутри уцелели пластины стального и алюминиевого шасси, медные радиаторы охлаждения процессоров, пучки вырванных медных и позолоченных шлейфов, кремниевые пластины, накопители данных и электронные схемы.
* **Содержимое:**
  * `hbm:item.plate_steel` (**Листовая сталь корпуса**) | Кол-во: 2–4 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.plate_aluminium` (**Алюминиевая направляющая шасси**) | Кол-во: 2–4 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.plate_copper` (**Медный радиатор охлаждения**) | Кол-во: 1–3 | Вес: 55 | `RandomAmount="true"`
  * `hbm:item.wire_fine:2900` (**Тонкий медный провод (шлейфы)**) | Кол-во: 6–12 | Вес: 70 | `RandomAmount="true"`
  * `hbm:item.wire_fine:7900` (**Позолоченный тонкий провод**) | Кол-во: 2–6 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.wire_fine:38` (**Сверхпроводящий провод шины**) | Кол-во: 2–4 | Вес: 35 | `RandomAmount="true"`
  * `hbm:item.crt_display` (**ЭЛТ-дисплей индикации**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.circuit:15` (**Корпус блока управления**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.circuit:10` (**Военная микросхема**) | Кол-во: 1–2 | Вес: 40 | `RandomAmount="true"`
  * `hbm:item.circuit:11` (**Сложная схема**) | Кол-во: 1–2 | Вес: 30 | `RandomAmount="true"`
  * `hbm:item.circuit:12` (**Сверхсложная схема**) | Кол-во: 1–2 | Вес: 20 | `RandomAmount="true"`
  * `hbm:item.circuit:16` (**Интерфейсная плата шины**) | Кол-во: 1 | Вес: 25 | `LimitedDropCount="0"` | `RandomAmount="false"`
  * `hbm:item.circuit:13` (**Вычислительный процессор**) | Кол-во: 1 | Вес: 25 | `LimitedDropCount="0"` | `RandomAmount="false"`
  * `hbm:item.circuit:6` (**Интегральная микросхема**) | Кол-во: 2–4 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.billet_silicon` (**Кремниевая заготовка**) | Кол-во: 3–6 | Вес: 50 | `RandomAmount="true"`
  * `OpenComputers:item:19` (**Магнитный металлический диск OC**) | Кол-во: 1–2 | Вес: 45 | `RandomAmount="true"`
  * `computronics:computronics.tape:2` (**Алмазная кассета данных**) | Кол-во: 1 | Вес: 30 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.hard_drive` (**Жёсткий диск NTM**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.coil_gold` (**Золотая катушка**) | Кол-во: 2–4 | Вес: 35 | `RandomAmount="true"`
  * `hbm:item.coil_gold_torus` (**Тороидальная катушка**) | Кол-во: 1–3 | Вес: 30 | `RandomAmount="true"`
  * `hbm:item.circuit:21` (**Квантовые часы (NTP эталон)**) | Кол-во: 1 | Вес: 15 | `LimitedDropCount="1"` | `RandomAmount="false"`

---

### Блок 100–129: Оружие, патроны и обвесы

#### 🎒 [Meta 100] Вскрытый патронный цинк
* **Уровень:** 1 | **Rarity:** 0 | **Дроп:** `2 - 3` | `CombineTrashGroup="true"`
* *Лор:* Вскрытая консервным ножом оцинкованная коробка, валявшаяся на полу заброшенного караульного помещения. Патроны почти полностью выгребли до нас: внутри осталась промасленная упаковочная бумага, ветошь для чистки стволов, стреляные гильзы, щепотка пороха и считанные патроны на донышке.
* **Содержимое:**
  * `minecraft:paper` (**Промасленная упаковочная бумага**) | Кол-во: 2–4 | Вес: 85 | `RandomAmount="true"`
  * `hbm:item.rag` (**Ветошь для чистки стволов**) | Кол-во: 2–4 | Вес: 85 | `RandomAmount="true"`
  * `hbm:item.casing:0` (**Малая бронзовая гильза**) | Кол-во: 6–12 | Вес: 75 | `RandomAmount="true"`
  * `hbm:item.casing:1` (**Малая стальная гильза**) | Кол-во: 6–12 | Вес: 75 | `RandomAmount="true"`
  * `hbm:item.casing:4` (**Пластиковая гильза 12k**) | Кол-во: 4–8 | Вес: 65 | `RandomAmount="true"`
  * `minecraft:gunpowder` (**Порох**) | Кол-во: 4–8 | Вес: 70 | `RandomAmount="true"`
  * `hbm:item.powder_lead` (**Свинцовый порошок**) | Кол-во: 4–8 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.debris_shrapnel` (**Шрапнель / картечь**) | Кол-во: 8–16 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.ammo_standard:21` (**Патрон 9мм FMJ**) | Кол-во: 4–8 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.ammo_standard:25` (**Патрон 5.56мм FMJ**) | Кол-во: 4–8 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.ammo_standard:29` (**Патрон 7.62мм FMJ**) | Кол-во: 4–8 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.pellet_buckshot` (**Картечь 12k**) | Кол-во: 4–8 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.ammo_standard:1` (**Патрон .22 LR AP**) | Кол-во: 8–16 | Вес: 40 | `RandomAmount="true"`
  * `hbm:item.gun_kit_1` (**Оружейный набор чистки**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.casing_bag` (**Сумка гильзоулавливателя**) | Кол-во: 1 | Вес: 30 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `minecraft:iron_ingot` (**Обрезок жестяной крышки**) | Кол-во: 1–2 | Вес: 65 | `RandomAmount="true"`
  * `minecraft:flint` (**Кремень**) | Кол-во: 2–4 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.stick_dynamite` (**Динамитная шашка**) | Кол-во: 1–2 | Вес: 35 | `RandomAmount="true"`
  * `hbm:item.grenade_lemon` (**Граната «Лимонка»**) | Кол-во: 1 | Вес: 25 | `LimitedDropCount="1"` | `RandomAmount="false"`

#### 🎖️ [Meta 101] Запечатанный патронный цинк
* **Уровень:** 2 | **Rarity:** 1 | **Дроп:** `1` (Моно-навал) | `CombineTrashGroup="false"`
* *Лор:* Заводской оцинкованный ящик в консервационной пушечной смазке, герметично запаянный на оборонном заводе. Вскрывается со скрежетом и гарантированно выдаёт полноценную заводскую укладку боеприпасов строго одного калибра — без примеси постороннего хлама.
* **Калиберные комплекты (`ItemGroup`):** Ровно один гарантированный ролл выдаёт весь неделимый боекомплект:
  * `pack_762_zinc` (**Партия 7.62×39 мм**, Вес: 85): `hbm:item.ammo_standard:29` (7.62мм FMJ, 64 шт.) + `hbm:item.ammo_standard:31` (7.62мм AP, 16 шт.) + `hbm:item.casing:1` (Малая стальная гильза, 32 шт.)
  * `pack_556_zinc` (**Партия 5.56×45 мм**, Вес: 85): `hbm:item.ammo_standard:25` (5.56мм FMJ, 64 шт.) + `hbm:item.ammo_standard:26` (5.56мм AP, 16 шт.) + `hbm:item.casing:1` (Малая стальная гильза, 32 шт.)
  * `pack_9mm_zinc` (**Партия 9×19 мм Parabellum**, Вес: 80): `hbm:item.ammo_standard:21` (9мм FMJ, 64 шт.) + `hbm:item.ammo_standard:22` (9мм AP, 32 шт.) + `hbm:item.ammo_standard:23` (9мм HP, 32 шт.) + `hbm:item.casing:0` (Малая бронзовая гильза, 48 шт.)
  * `pack_12ga_zinc` (**Партия 12 Gauge**, Вес: 75): `hbm:item.pellet_buckshot` (Картечь, 32 шт.) + `hbm:item.casing:4` (Пластиковая гильза 12k, 24 шт.) + `hbm:item.cordite` (Кордит, 8 шт.)
  * `pack_22lr_zinc` (**Партия .22 LR**, Вес: 65): `hbm:item.ammo_standard:0` (.22 LR FMJ, 64 шт.) + `hbm:item.ammo_standard:1` (.22 LR AP, 32 шт.) + `hbm:item.casing:0` (Малая бронзовая гильза, 32 шт.)
  * `pack_magnum_zinc` (**Партия .44 / .357 Magnum**, Вес: 60): `hbm:item.ammo_standard:4` (.44 Mag, 32 шт.) + `hbm:item.ammo_standard:6` (.357 обойма, 4 шт.) + `hbm:item.casing:2` (Большая бронзовая гильза, 24 шт.)
  * `pack_50bmg_zinc` (**Партия .50 BMG**, Вес: 40): `hbm:item.ammo_standard:34` (.50 BMG FMJ, 16 шт.) + `hbm:item.ammo_standard:35` (.50 BMG AP, 8 шт.) + `hbm:item.casing:5` (Гильза .50 BMG, 12 шт.)

#### 🪖 [Meta 102] Оружейный кофр
* **Уровень:** 3 | **Rarity:** 2 | **Дроп:** `2 - 4` | `CombineTrashGroup="false"`
* *Лор:* Бронированный армейский оружейный кофр спецподразделений.
* **Содержимое:**
  * `hbm:item.plate_kevlar` (**Лист кевлара**) | Кол-во: 1–2 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.weapon_mod_special:0` (**Тактический глушитель**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.weapon_mod_special:1` (**Оптический прицел**) | Кол-во: 1 | Вес: 30 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.ammo_standard:34` (**Патрон .50 BMG FMJ**) | Кол-во: 12–24 | Вес: 40 | `RandomAmount="true"`
  * `hbm:item.ammo_standard:35` (**Патрон .50 BMG AP**) | Кол-во: 8–16 | Вес: 35 | `RandomAmount="true"`
  * `hbm:item.ammo_standard:53` (**40-мм граната HE**) | Кол-во: 2–4 | Вес: 35 | `RandomAmount="true"`
  * `hbm:item.ammo_standard:54` (**40-мм кумулятивная граната**) | Кол-во: 1–3 | Вес: 30 | `RandomAmount="true"`
  * `hbm:item.plate_armor_hev` (**Бронеплита HEV**) | Кол-во: 1–2 | Вес: 35 | `RandomAmount="true"`
  * `hbm:item.plate_armor_titanium` (**Титановая бронеплита**) | Кол-во: 1–2 | Вес: 40 | `RandomAmount="true"`
  * `hbm:item.grenade_shell` (**Корпус гранаты**) | Кол-во: 1–2 | Вес: 35 | `RandomAmount="true"`
  * `hbm:item.grenade_fuze` (**Взрыватель гранаты**) | Кол-во: 1–2 | Вес: 35 | `RandomAmount="true"`
  * `hbm:item.casing:5` (**Гильза .50 BMG**) | Кол-во: 6–12 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.stealth_boy` (**Стелс-Бой**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.cordite` (**Кордит**) | Кол-во: 6–12 | Вес: 55 | `RandomAmount="true"`
  * `hbm:item.ammo_container` (**Патронный ящик**) | Кол-во: 1 | Вес: 25 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.gun_kit_1` (**Оружейный набор I**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.plate_combine_steel` (**Пластина стали Альянса**) | Кол-во: 1–2 | Вес: 30 | `RandomAmount="true"`
  * `hbm:item.laser_crystal_co2` (**CO2 лазерный кристалл**) | Кол-во: 1 | Вес: 20 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.ammo_standard:36` (**Патрон .50 BMG Зажигательный**) | Кол-во: 6–12 | Вес: 30 | `RandomAmount="true"`

---

### Блок 130–159: Кулинария, провизия и быт

#### 🥫 [Meta 130] Котелок бродяги
* **Уровень:** 1 | **Rarity:** 0 | **Дроп:** `1 - 3` | `CombineTrashGroup="true"`
* *Лор:* Скромные пожитки скитальца пустошей. Содержит только продукты длительного хранения (сухари, вяленое мясо, консервы, семечки, муку) и утварь для костра — никакой скоропортящейся еды.
* **Содержимое:**
  * `harvestcraft:saltItem` (**Соль**) | Кол-во: 4–8 | Вес: 90 | `RandomAmount="true"`. *Смысл:* Консервант и кулинарный компонент.
  * `minecraft:flint_and_steel` (**Огниво**) | Кол-во: 1 | Вес: 60 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `cfm:ItemSoap` (**Мыло**) | Кол-во: 1 | Вес: 50 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `cfm:ItemRecipeBook` (**Книга рецептов**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `SpiceOfLife:bookfoodjournal` (**Дневник питания**) | Кол-во: 1 | Вес: 50 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `minecraft:potion` (**Колба с водой**) | Кол-во: 1 | Вес: 80 | `RandomAmount="false"`
  * `harvestcraft:saltedsunflowerseedsItem` (**Солёные семечки**) | Кол-во: 2–4 | Вес: 70 | `RandomAmount="true"`
  * `harvestcraft:crackerItem` (**Крекер**) | Кол-во: 3–6 | Вес: 80 | `RandomAmount="true"`
  * `hbm:item.canned_conserve:1` (**Консервированная рыба**) | Кол-во: 1 | Вес: 65 | `RandomAmount="false"`
  * `harvestcraft:beefjerkyItem` (**Вяленая говядина**) | Кол-во: 1–3 | Вес: 70 | `RandomAmount="true"`
  * `hbm:item.canned_conserve:3` (**Паштет**) | Кол-во: 1 | Вес: 65 | `RandomAmount="false"`
  * `minecraft:bowl` (**Миска**) | Кол-во: 1–2 | Вес: 65 | `RandomAmount="true"`
  * `minecraft:sugar` (**Сахар**) | Кол-во: 3–6 | Вес: 70 | `RandomAmount="true"`
  * `minecraft:coal` (**Уголь**) | Кол-во: 3–6 | Вес: 75 | `RandomAmount="true"`
  * `hbm:item.flour` (**Мука**) | Кол-во: 2–4 | Вес: 65 | `RandomAmount="true"`
  * `harvestcraft:trailmixItem` (**Сухофрукты и орехи**) | Кол-во: 1–3 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.canned_conserve:0` (**Консервированная говядина**) | Кол-во: 1 | Вес: 55 | `RandomAmount="false"`
  * `hbm:item.can_mug` (**Жестяная кружка**) | Кол-во: 1 | Вес: 55 | `RandomAmount="false"`
  * `hbm:item.can_empty` (**Пустая банка**) | Кол-во: 1–2 | Вес: 60 | `RandomAmount="true"`

#### 🍳 [Meta 131] Походный кухонник
* **Уровень:** 2 | **Rarity:** 1 | **Дроп:** `2 - 3` | `CombineTrashGroup="true"`
* *Лор:* Поварская утварь из армейского полевого пищеблока.
* **Содержимое:**
  * `cfm:ItemKnife` (**Кухонный нож**) | Кол-во: 1 | Вес: 55 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `harvestcraft:potItem` (**Горшок / Котелок**) | Кол-во: 1 | Вес: 60 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `harvestcraft:skilletItem` (**Сковорода**) | Кол-во: 1 | Вес: 60 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `harvestcraft:saucepanItem` (**Кастрюля**) | Кол-во: 1 | Вес: 50 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `harvestcraft:bakewareItem` (**Форма для выпечки**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.briquette:2` (**Брикет опилок**) | Кол-во: 4–8 | Вес: 80 | `RandomAmount="true"`
  * `minecraft:sugar` (**Сахар**) | Кол-во: 4–8 | Вес: 70 | `RandomAmount="true"`
  * `harvestcraft:cuttingboardItem` (**Разделочная доска**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `harvestcraft:juicerItem` (**Соковыжималка**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `harvestcraft:mixingbowlItem` (**Чаша для смешивания**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `harvestcraft:mortarandpestleItem` (**Ступка и пестик**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.can_key` (**Консервный ключ**) | Кол-во: 1 | Вес: 50 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.canned_conserve:0` (**Консервированная говядина**) | Кол-во: 1–2 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.canned_conserve:16` (**Тушеные грибы**) | Кол-во: 1–2 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.canned_conserve:1` (**Консервированная рыба**) | Кол-во: 1–2 | Вес: 55 | `RandomAmount="true"`
  * `hbm:item.canned_conserve:3` (**Паштет**) | Кол-во: 1–2 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.can_smart` (**Умная кола**) | Кол-во: 1 | Вес: 50 | `RandomAmount="false"`
  * `hbm:item.can_creature` (**Энергетик Creature**) | Кол-во: 1 | Вес: 50 | `RandomAmount="false"`
  * `harvestcraft:oliveoilItem` (**Оливковое масло**) | Кол-во: 1–2 | Вес: 55 | `RandomAmount="true"`
  * `hbm:item.canned_conserve:0` (**Армейская тушёнка**) | Кол-во: 1–2 | Вес: 55 | `RandomAmount="true"`

#### 🍽️ [Meta 132] Офицерский паёк
* **Уровень:** 3 | **Rarity:** 2 | **Дроп:** `3 - 4` | `CombineTrashGroup="false"`
* *Лор:* Неприкосновенный запас провизии командного состава из защищённого бункера ГО: элитные консервы, концентрат кофе и чая, чистый спирт, шоколад и руководство шеф-повара.
* **Содержимое:**
  * `cookingforblockheads:recipebook:3` (**Кулинарная книга II**) | Кол-во: 1 | Вес: 30 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Автокрафт рецептов кухни.
  * `hbm:item.canned_conserve:0` (**Говядина высшего сорта**) | Кол-во: 2–4 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.canned_conserve:16` (**Тушеные грибы**) | Кол-во: 2–4 | Вес: 50 | `RandomAmount="true"`
  * `minecraft:sugar` (**Сахар**) | Кол-во: 4–8 | Вес: 65 | `RandomAmount="true"`
  * `harvestcraft:saltItem` (**Соль**) | Кол-во: 12–24 | Вес: 80 | `RandomAmount="true"`
  * `hbm:item.canteen_vodka` (**Фляга с водкой**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.canned_conserve:1` (**Консервированная рыба**) | Кол-во: 1–3 | Вес: 55 | `RandomAmount="true"`
  * `hbm:item.chocolate` (**Шоколад**) | Кол-во: 1–2 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.powder_coffee` (**Растворимый кофе**) | Кол-во: 2–4 | Вес: 55 | `RandomAmount="true"`
  * `hbm:item.tea_leaf` (**Чайный лист**) | Кол-во: 2–4 | Вес: 55 | `RandomAmount="true"`
  * `hbm:item.can_breen` (**Вода Брина**) | Кол-во: 1–2 | Вес: 40 | `RandomAmount="true"`
  * `hbm:item.bottle_nuka` (**Ядер-Кола**) | Кол-во: 2–4 | Вес: 45 | `LimitedDropCount="2"` | `RandomAmount="true"`
  * `hbm:item.bottle_opener` (**Открывашка**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `harvestcraft:beefjerkyItem` (**Вяленая говядина**) | Кол-во: 2–4 | Вес: 55 | `RandomAmount="true"`
  * `hbm:item.can_key` (**Консервный ключ**) | Кол-во: 1 | Вес: 50 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.can_smart` (**Умная кола**) | Кол-во: 1–2 | Вес: 45 | `RandomAmount="true"`
  * `harvestcraft:crackerItem` (**Крекер**) | Кол-во: 3–6 | Вес: 55 | `RandomAmount="true"`
  * `hbm:item.twinkie` (**Твинки**) | Кол-во: 1–2 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.can_redbomb` (**Энергетик Red Bomb**) | Кол-во: 1–2 | Вес: 40 | `RandomAmount="true"`

---

### Блок 160–189: Агрономия, оранжерея и банк семян

#### 🌾 [Meta 160] Семена с огорода
* **Уровень:** 1 | **Rarity:** 0 | **Дроп:** `2 - 3` | `CombineTrashGroup="true"`
* *Лор:* Посевной материал из погребов частных домов, дач и приусадебных парников.
* **Содержимое (по 2–4 шт., `RandomAmount="true"`):**
  * `minecraft:wheat_seeds` (**Семена пшеницы**) | Кол-во: 3–6 | Вес: 90
  * `minecraft:potato` (**Картофель**) | Кол-во: 2–4 | Вес: 85
  * `minecraft:carrot` (**Морковь**) | Кол-во: 2–4 | Вес: 85
  * `minecraft:pumpkin_seeds` (**Семена тыквы**) | Кол-во: 2–4 | Вес: 75
  * `minecraft:melon_seeds` (**Семена арбуза**) | Кол-во: 2–4 | Вес: 75
  * `harvestcraft:tomatoseedItem` (**Семя помидора**) | Вес: 80
  * `harvestcraft:cabbageseedItem` (**Семя капусты**) | Вес: 80
  * `harvestcraft:cucumberseedItem` (**Семя огурца**) | Вес: 75
  * `harvestcraft:onionseedItem` (**Семя лука**) | Вес: 75
  * `harvestcraft:garlicseedItem` (**Семя чеснока**) | Вес: 75
  * `harvestcraft:peasseedItem` (**Семя гороха**) | Вес: 75
  * `harvestcraft:beanseedItem` (**Семя фасоли**) | Вес: 75
  * `harvestcraft:beetseedItem` (**Семя свёклы**) | Вес: 70
  * `harvestcraft:lettuceseedItem` (**Семя латука**) | Вес: 70
  * `harvestcraft:spinachseedItem` (**Семя шпината**) | Вес: 70
  * `harvestcraft:scallionseedItem` (**Семя шалота**) | Вес: 70
  * `harvestcraft:zucchiniseedItem` (**Семя цукини**) | Вес: 70
  * `harvestcraft:cornseedItem` (**Семя кукурузы**) | Вес: 75
  * `minecraft:bone` (**Кость**) | Кол-во: 2–4 | Вес: 65

#### 🚜 [Meta 161] Посевной мешок
* **Уровень:** 2 | **Rarity:** 1 | **Дроп:** `2 - 4` | `CombineTrashGroup="true"`
* *Лор:* Посевной материал опытной агростанции: технические, белковые, масличные и тонизирующие культуры.
* **Содержимое (по 3–6 шт., `RandomAmount="true"`):**
  * `harvestcraft:cottonseedItem` (**Семя хлопка**) | Вес: 85. *Смысл:* Нити, ткань, перевязки, фильтры.
  * `harvestcraft:soybeanseedItem` (**Семя сои**) | Вес: 80. *Смысл:* Соевый белок (тофу, молоко, масло).
  * `harvestcraft:riceseedItem` (**Семя риса**) | Вес: 75
  * `harvestcraft:barleyseedItem` (**Семя ячменя**) | Вес: 75
  * `harvestcraft:ryeseedItem` (**Семя ржи**) | Вес: 75
  * `harvestcraft:oatsseedItem` (**Семя овса**) | Вес: 75
  * `harvestcraft:sunflowerseedItem` (**Семя подсолнуха**) | Вес: 70
  * `harvestcraft:mustardseedItem` (**Семя горчицы**) | Вес: 70
  * `harvestcraft:chilipepperseedItem` (**Семя перца чили**) | Вес: 65
  * `harvestcraft:bellpepperseedItem` (**Семя болгарского перца**) | Вес: 65
  * `harvestcraft:eggplantseedItem` (**Семя баклажана**) | Вес: 60
  * `harvestcraft:leekseedItem` (**Семя лука-порея**) | Вес: 60
  * `harvestcraft:coffeeseedItem` (**Семя кофе**) | Кол-во: 2–4 | Вес: 60
  * `harvestcraft:teaseedItem` (**Семя чая**) | Кол-во: 2–4 | Вес: 60
  * `harvestcraft:sweetpotatoseedItem` (**Семя батата**) | Вес: 65
  * `minecraft:dye:15` (**Костная мука**) | Кол-во: 4–8 | Вес: 75
  * `hbm:item.plant_item:1` (**Лист фиолетового камыша**) | Кол-во: 1–3 | Вес: 50
  * `hbm:item.plant_item:2` (**Лист горчичной ивы**) | Кол-во: 1–3 | Вес: 50
  * `harvestcraft:honeycombItem` (**Пчелиные соты**) | Кол-во: 2–4 | Вес: 55
  * `exnihilo:crook` (**Посох**) | Кол-во: 1 | Вес: 60 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Крюк сбора листвы и саженцев.

#### 🌱 [Meta 162] Банк семян
* **Уровень:** 3 | **Rarity:** 2 | **Дроп:** `1` (Оптовая целевая партия одного сорта семян: стек до 32 шт.) | `CombineTrashGroup="false"`
* *Лор:* Герметичный вакуумный контейнер криохранилища Всемирного банка семян. При открытии выдаёт полный оптовый стек одного конкретного сорта для масштабного запуска гидропонной оранжереи.
* **Содержимое пула (ровно 1 позиция, Кол-во: до 32 шт., `RandomAmount="true"`):**
  * `minecraft:wheat_seeds` (**Семена пшеницы**) | Вес: 80
  * `minecraft:potato` (**Семенной картофель**) | Вес: 80
  * `minecraft:carrot` (**Семенная морковь**) | Вес: 80
  * `harvestcraft:tomatoseedItem` (**Семя томата**) | Вес: 75
  * `harvestcraft:cabbageseedItem` (**Семя капусты**) | Вес: 75
  * `harvestcraft:cornseedItem` (**Семя кукурузы**) | Вес: 75
  * `harvestcraft:cottonseedItem` (**Семя хлопка**) | Вес: 85
  * `harvestcraft:soybeanseedItem` (**Семя сои**) | Вес: 85
  * `harvestcraft:riceseedItem` (**Семя риса**) | Вес: 75
  * `harvestcraft:barleyseedItem` (**Семя ячменя**) | Вес: 75
  * `harvestcraft:ryeseedItem` (**Семя ржи**) | Вес: 75
  * `harvestcraft:oatsseedItem` (**Семя овса**) | Вес: 75
  * `harvestcraft:sunflowerseedItem` (**Семя подсолнуха**) | Вес: 70
  * `harvestcraft:garlicseedItem` (**Семя чеснока**) | Вес: 70
  * `harvestcraft:onionseedItem` (**Семя лука**) | Вес: 70
  * `harvestcraft:cucumberseedItem` (**Семя огурца**) | Вес: 70
  * `harvestcraft:peasseedItem` (**Семя гороха**) | Вес: 70
  * `harvestcraft:beanseedItem` (**Семя фасоли**) | Вес: 70
  * `harvestcraft:beetseedItem` (**Семя свёклы**) | Вес: 70
  * `harvestcraft:coffeeseedItem` (**Семя кофе**) | Вес: 65

---

### Блок 190–219: Строительство, отделка и чертежи

#### 🪚 [Meta 190] Мешок плотника
* **Уровень:** 1 | **Rarity:** 0 | **Дроп:** `2 - 4` | `CombineTrashGroup="true"`
* *Лор:* Походный холщовый мешок столяра-каркасника. Содержит солидный запас крепёжных метизов, строительных шнуров и нитей, мелков для разметки, шаблонов ящиков, деревянной фурнитуры Carpenter's и ручных инструментов для возведения каркасов и первичного обустройства склада.
* **Содержимое:**
  * `minecraft:string` (**Крепкая нить / дратва**) | Кол-во: 16–32 | Вес: 85 | `RandomAmount="true"`
  * `modernmarkings:item.chalk` (**Разметочный мел**) | Кол-во: 8–16 | Вес: 80 | `RandomAmount="true"`
  * `hbm:item.bolt` (**Строительные болты / метизы NTM**) | Кол-во: 16–32 | Вес: 75 | `RandomAmount="true"`
  * `hbm:item.pin` (**Шпильки / штифты NTM**) | Кол-во: 8–16 | Вес: 75 | `RandomAmount="true"`
  * `minecraft:torch` (**Факелы**) | Кол-во: 16–32 | Вес: 75 | `RandomAmount="true"`
  * `OpenBlocks:generic:5` (**Строительный шнур**) | Кол-во: 8–16 | Вес: 70 | `RandomAmount="true"`
  * `StorageDrawers:upgradeTemplate` (**Шаблон улучшения ящика**) | Кол-во: 8–16 | Вес: 70 | `RandomAmount="true"`
  * `minecraft:ladder` (**Лестница**) | Кол-во: 8–16 | Вес: 70 | `RandomAmount="true"`
  * `minecraft:sign` (**Табличка**) | Кол-во: 4–8 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.crayon` (**Строительные восковые мелки NTM**) | Кол-во: 4–8 | Вес: 65 | `RandomAmount="true"`
  * `StorageDrawers:tape` (**Упаковочный скотч**) | Кол-во: 1 | Вес: 60 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `StorageDrawers:upgradeLock` (**Ключ блокировки ящика**) | Кол-во: 2–4 | Вес: 60 | `RandomAmount="true"`
  * `exnihilo:crook` (**Посох плотника**) | Кол-во: 1 | Вес: 60 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `CarpentersBlocks:blockCarpentersButton` (**Кнопка плотника**) | Кол-во: 2–4 | Вес: 50 | `RandomAmount="true"`
  * `CarpentersBlocks:blockCarpentersTorch` (**Факел плотника**) | Кол-во: 4–8 | Вес: 50 | `RandomAmount="true"`
  * `CarpentersBlocks:itemCarpentersHammer` (**Молоток плотника**) | Кол-во: 1 | Вес: 55 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `CarpentersBlocks:itemCarpentersChisel` (**Стамеска Carpenter's**) | Кол-во: 1 | Вес: 55 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `cfm:ItemHammer` (**Столярный молоток**) | Кол-во: 1 | Вес: 50 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `BiblioCraft:item.FramingSaw` (**Каркасная пила**) | Кол-во: 1 | Вес: 50 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.screwdriver` (**Отвёртка NTM**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `BiblioCraft:item.PlumbLine` (**Строительный отвес**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `BiblioCraft:item.tapeMeasure` (**Мерная лента**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `BiblioCraft:item.StockroomCatalog` (**Складской каталог**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.padlock_rusty` (**Ржавый замок**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`

#### 🎨 [Meta 191] Сумка маляра
* **Уровень:** 2 | **Rarity:** 1 | **Дроп:** `2 - 4` | `CombineTrashGroup="true"`
* *Лор:* Походная холщовая сумка промышленного маляра-оформителя. Забита базовыми и химическими красителями для сигнальной разметки цехов, полос опасности и труб, трафаретной бумагой, чистящей ветошью, банками и инструментами для быстрой окраски и декорирования базы.
* **Содержимое:**
  * `etfuturum:dye:0` (**Белый краситель**) | Кол-во: 16–32 | Вес: 85 | `RandomAmount="true"`
  * `etfuturum:dye:3` (**Чёрный краситель**) | Кол-во: 16–32 | Вес: 85 | `RandomAmount="true"`
  * `hbm:item.chemical_dye:11` (**Хим. краситель: жёлтый / полосы опасности**) | Кол-во: 16–32 | Вес: 80 | `RandomAmount="true"`
  * `hbm:item.chemical_dye:1` (**Хим. краситель: сигнальный красный**) | Кол-во: 16–32 | Вес: 80 | `RandomAmount="true"`
  * `hbm:item.chemical_dye:0` (**Хим. краситель: чёрный**) | Кол-во: 16–32 | Вес: 75 | `RandomAmount="true"`
  * `hbm:item.chemical_dye:15` (**Хим. краситель: белый**) | Кол-во: 16–32 | Вес: 75 | `RandomAmount="true"`
  * `hbm:item.chemical_dye:4` (**Хим. краситель: синий**) | Кол-во: 12–24 | Вес: 70 | `RandomAmount="true"`
  * `etfuturum:dye:1` (**Синий краситель EtFuturum**) | Кол-во: 12–24 | Вес: 70 | `RandomAmount="true"`
  * `etfuturum:dye:2` (**Коричневый краситель EtFuturum**) | Кол-во: 12–24 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.chemical_dye:2` (**Хим. краситель: зелёный**) | Кол-во: 8–16 | Вес: 55 | `RandomAmount="true"`
  * `hbm:item.chemical_dye:14` (**Хим. краситель: оранжевый**) | Кол-во: 8–16 | Вес: 55 | `RandomAmount="true"`
  * `hbm:item.chemical_dye:10` (**Хим. краситель: лаймовый**) | Кол-во: 8–16 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.chemical_dye:6` (**Хим. краситель: бирюзовый / Cyan**) | Кол-во: 8–16 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.chemical_dye:8` (**Хим. краситель: серый**) | Кол-во: 8–16 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.chemical_dye:5` (**Хим. краситель: фиолетовый**) | Кол-во: 8–16 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.chemical_dye:13` (**Хим. краситель: пурпурный**) | Кол-во: 8–16 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.chemical_dye:9` (**Хим. краситель: розовый**) | Кол-во: 8–16 | Вес: 40 | `RandomAmount="true"`
  * `hbm:item.rag` (**Ветошь для затирки**) | Кол-во: 4–8 | Вес: 75 | `RandomAmount="true"`
  * `hbm:item.can_empty` (**Пустая жестяная банка**) | Кол-во: 2–4 | Вес: 70 | `RandomAmount="true"`
  * `modernmarkings:item.chalk` (**Разметочный мел**) | Кол-во: 4–8 | Вес: 70 | `RandomAmount="true"`
  * `hbm:item.crayon` (**Маркировочные мелки NTM**) | Кол-во: 2–4 | Вес: 60 | `RandomAmount="true"`
  * `minecraft:paper` (**Трафаретная бумага**) | Кол-во: 4–8 | Вес: 65 | `RandomAmount="true"`
  * `minecraft:book` (**Журнал палитр**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `BiblioCraft:item.PaintingCanvas` (**Холст для картин/вывесок**) | Кол-во: 2–4 | Вес: 50 | `RandomAmount="true"`
  * `OpenBlocks:paintbrush` (**Малярная кисть**) | Кол-во: 1 | Вес: 50 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `OpenBlocks:paintcan` (**Банка с краской**) | Кол-во: 1–2 | Вес: 50 | `RandomAmount="true"`
  * `OpenBlocks:squeegee` (**Резиновая швабра**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `ProjectBlue:emptySprayCan` (**Баллончик-распылитель**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `chisel:chisel` (**Стамеска Chisel**) | Кол-во: 1 | Вес: 55 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `BiblioCraft:item.BiblioDrill` (**Аккумуляторный шуруповёрт**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `OpenBlocks:generic:11` (**Графитовый карандаш**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `OpenBlocks:crayonGlasses` (**Цветные очки для мелков**) | Кол-во: 1 | Вес: 25 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `ArchitectureCraft:hammer` (**Молоток ArchitectureCraft**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `ArchitectureCraft:chisel` (**Резец ArchitectureCraft**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`

#### 🏛️ [Meta 192] Кейс проектировщика
* **Уровень:** 3 | **Rarity:** 2 | **Дроп:** `3 - 5` | `CombineTrashGroup="false"`
* *Лор:* Комплект главного архитектора (система пространственного CAD-прототипирования).
* **Содержимое:**
  * `hbm:item.blueprint_folder` (**Буклет чертежей**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `OpenBlocks:imaginary:0` (**Магический карандаш**) | Кол-во: 1 | Вес: 30 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Рисование временных блоков-лесов в воздухе.
  * `OpenBlocks:imaginary:1` (**Цветной карандаш**) | Кол-во: 1 | Вес: 30 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `OpenBlocks:pencilGlasses` (**Очки чертёжника**) | Кол-во: 1 | Вес: 25 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Делают нарисованные блоки твёрдыми для ходьбы.
  * `OpenBlocks:crayonGlasses` (**Цветные очки**) | Кол-во: 1 | Вес: 25 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `OpenBlocks:epicEraser` (**Epic Eraser**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Моментальное стирание блоков-лесов.
  * `ArchitectureCraft:hammer` (**Молоток архитектора**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `ArchitectureCraft:chisel` (**Резец архитектора**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `ArchitectureCraft:glowbrush` (**Светящаяся кисть**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Скрытая люминесцентная подсветка блоков.
  * `ProjectBlue:emptySprayCan` (**Баллончик распылителя**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `ArchitectureCraft:sawbench` (**Распиловочный верстак**) | Кол-во: 1 | Вес: 30 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `minecraft:dye:8`..`:14` (**Набор редких красителей**) | Кол-во: 6–12 | Вес: 70 | `RandomAmount="true"`
  * `OpenBlocks:sonicglasses` (**Звуковые очки**) | Кол-во: 1 | Вес: 20 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `OpenBlocks:heightMap` (**Карта высот**) | Кол-во: 1 | Вес: 30 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.crt_display` (**ЭЛТ-дисплей**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `ProjRed|Expansion:projectred.expansion.plan` (**Recipe Plan**) | Кол-во: 1 | Вес: 30 | `LimitedDropCount="1"` | `RandomAmount="false"`

---

### Блок 220–249: Геологоразведка и горное дело

#### 🧭 [Meta 220] Планшет разведчика
* **Уровень:** 1 | **Rarity:** 0 | **Дроп:** `1 - 2` | `CombineTrashGroup="true"`
* *Лор:* Полевой планшет маркшейдера и картографа штолен.
* **Содержимое:**
  * `OpenBlocks:pedometer` (**Шагомер**) | Кол-во: 1 | Вес: 60 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `OpenBlocks:flag` (**Сигнальный флаг**) | Кол-во: 2–4 | Вес: 65 | `RandomAmount="true"`. *Смысл:* Маркировка маршрута в шахтах.
  * `minecraft:map` (**Карта**) | Кол-во: 1 | Вес: 80 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `BiblioCraft:item.BiblioWayPointCompass` (**Координатный компас**) | Кол-во: 1 | Вес: 50 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `BiblioCraft:item.BigBook` (**Большая письменная книга**) | Кол-во: 1 | Вес: 20 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `BiblioCraft:item.BiblioClipboard` (**Планшет с зажимом**) | Кол-во: 1 | Вес: 50 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `minecraft:compass` (**Компас**) | Кол-во: 1 | Вес: 70 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `minecraft:clock` (**Часы**) | Кол-во: 1 | Вес: 60 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `minecraft:paper` (**Бумага**) | Кол-во: 4–8 | Вес: 75 | `RandomAmount="true"`
  * `minecraft:feather` (**Перо**) | Кол-во: 2–4 | Вес: 70 | `RandomAmount="true"`
  * `minecraft:torch` (**Факелы**) | Кол-во: 8–16 | Вес: 85 | `RandomAmount="true"`
  * `exnihilo:stone` (**Камни**) | Кол-во: 4–8 | Вес: 80 | `RandomAmount="true"`
  * `hbm:item.dust_tiny` (**Пыль**) | Кол-во: 4–8 | Вес: 75 | `RandomAmount="true"`
  * `minecraft:coal` (**Уголь**) | Кол-во: 3–6 | Вес: 75 | `RandomAmount="true"`
  * `minecraft:iron_ore` (**Железная руда**) | Кол-во: 1–2 | Вес: 60 | `RandomAmount="true"`
  * `minecraft:gold_nugget` (**Самородок золота**) | Кол-во: 2–4 | Вес: 55 | `RandomAmount="true"`
  * `minecraft:bucket` (**Ведро**) | Кол-во: 1 | Вес: 50 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `modernmarkings:item.chalk` (**Мел**) | Кол-во: 2–4 | Вес: 70 | `RandomAmount="true"`
  * `hbm:item.pin` (**Отмычка**) | Кол-во: 2–4 | Вес: 65 | `RandomAmount="true"`

#### 🛢️ [Meta 221] Геодезический набор
* **Уровень:** 2 | **Rarity:** 1 | **Дроп:** `2 - 3` | `CombineTrashGroup="true"`
* *Лор:* Комплект партии полевой геологоразведки стратегических ископаемых.
* **Содержимое:**
  * `hbm:item.oil_detector` (**Детектор нефти**) | Кол-во: 1 | Вес: 60 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Поиск подземных нефтяных линз и сланцевого газа NTM.
  * `hbm:item.survey_scanner` (**Спектральный сканер руд**) | Кол-во: 1 | Вес: 55 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Определение рудных жил чанка.
  * `BiblioCraft:item.BiblioGlasses` (**Очки монокль**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `OpenBlocks:infoBook` (**Справочник OpenBlocks**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.drillbit:0` (**Стальное сверло**) | Кол-во: 1 | Вес: 50 | `RandomAmount="false"`
  * `hbm:item.pipes_steel` (**Стальные трубы**) | Кол-во: 1 | Вес: 40 | `RandomAmount="false"`
  * `hbm:item.stick_dynamite` (**Динамит**) | Кол-во: 3–6 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.fluorite` (**Флюорит**) | Кол-во: 8–16 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.sulfur` (**Сера**) | Кол-во: 8–16 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.niter` (**Селитра**) | Кол-во: 8–16 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.powder_coal` (**Угольная пыль**) | Кол-во: 6–12 | Вес: 70 | `RandomAmount="true"`
  * `hbm:item.powder_iron` (**Железный порошок**) | Кол-во: 4–8 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.powder_copper` (**Медный порошок**) | Кол-во: 4–8 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.powder_lead` (**Свинцовый порошок**) | Кол-во: 4–8 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.gem_sodalite` (**Содалит**) | Кол-во: 3–6 | Вес: 55 | `RandomAmount="true"`
  * `hbm:item.coal_infernal` (**Инфернальный уголь**) | Кол-во: 4–8 | Вес: 55 | `RandomAmount="true"`
  * `minecraft:redstone` (**Редстоун**) | Кол-во: 8–16 | Вес: 75 | `RandomAmount="true"`
  * `minecraft:diamond` (**Алмаз**) | Кол-во: 1 | Вес: 20 | `RandomAmount="false"`
  * `hbm:item.geiger_counter` (**Счётчик Гейгера**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.bottle_mercury` (**Ртуть**) | Кол-во: 1–2 | Вес: 45 | `RandomAmount="true"`

#### 💎 [Meta 222] Спектрометр недр
* **Уровень:** 3 | **Rarity:** 2 | **Дроп:** `2 - 4` | `CombineTrashGroup="false"`
* *Лор:* Глубинная станция обнаружения неисчерпаемых рудных жил коренной породы.
* **Содержимое:**
  * `hbm:item.ore_density_scanner` (**Сканер плотности бедроковой руды**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.bobmazon` (**Устройство Бобмазон**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Орбитальный заказ снабжения Боба.
  * `hbm:item.drill_titanium` (**Титановый бур**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.drillbit:4` (**Деш-коронка**) | Кол-во: 1 | Вес: 30 | `RandomAmount="false"`
  * `hbm:item.drillbit:3` (**HSS-алмазная коронка**) | Кол-во: 1 | Вес: 25 | `RandomAmount="false"`
  * `hbm:item.chunk_ore`..`:2` (**Рудно-минеральные друзы**) | Кол-во: 1–2 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.crystal_iron` (**Железный кристалл**) | Кол-во: 2–4 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.gem_volcanic` (**Вулканический кристалл**) | Кол-во: 1 | Вес: 15 | `LimitedDropCount="2"` | `RandomAmount="false"`
  * `hbm:item.black_diamond` (**Чёрный алмаз**) | Кол-во: 1 | Вес: 15 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.powder_titanium` (**Титановый порошок**) | Кол-во: 2–4 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.powder_diamond` (**Алмазная пыль**) | Кол-во: 1–2 | Вес: 35 | `RandomAmount="true"`
  * `hbm:item.powder_gold` (**Золотой порошок**) | Кол-во: 2–4 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.ingot_tungsten` (**Вольфрам**) | Кол-во: 2–4 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.plate_armor_titanium` (**Титановая броня**) | Кол-во: 1–2 | Вес: 40 | `RandomAmount="true"`
  * `hbm:item.motor_desh` (**Деш-мотор**) | Кол-во: 1 | Вес: 25 | `RandomAmount="false"`
  * `hbm:item.circuit:10` (**Военный чип**) | Кол-во: 1 | Вес: 25 | `RandomAmount="false"`
  * `minecraft:diamond` (**Алмазы**) | Кол-во: 1–2 | Вес: 30 | `RandomAmount="true"`

---

### Блок 250–279: Связь, радио и сигнализация

#### 🚩 [Meta 250] Сумка сигнальщика
* **Уровень:** 1 | **Rarity:** 0 | **Дроп:** `2 - 3` | `CombineTrashGroup="true"`
* *Лор:* Снаряжение постового-сигнальщика и диспетчера горизонта: навигационные флажки, тонкий провод для растяжек, радиофакелы передачи сигнала, детонатор, мел, компас и предохранители.
* **Содержимое:**
  * `OpenBlocks:flag` (**Флаг**) | Кол-во: 1–2 | Вес: 65 | `RandomAmount="true"`. *Смысл:* Маркировочные штыри для разметки путей.
  * `hbm:item.wire_fine:2900` (**Тонкий провод**) | Кол-во: 6–12 | Вес: 75 | `RandomAmount="true"`
  * `hbm:tile.radio_torch_sender` (**Радиофакел-передатчик**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Беспроводная передача сигнала по частоте.
  * `hbm:tile.radio_torch_receiver` (**Радиофакел-приёмник**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Беспроводной приём сигнала (активация сирен/дверей).
  * `hbm:item.detonator` (**Детонатор**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Дистанционный подрыв зарядов.
  * `minecraft:redstone` (**Красная пыль**) | Кол-во: 8–16 | Вес: 80 | `RandomAmount="true"`
  * `minecraft:redstone_torch` (**Красный факел**) | Кол-во: 2–4 | Вес: 70 | `RandomAmount="true"`
  * `minecraft:lever` (**Рычаг**) | Кол-во: 1–2 | Вес: 65 | `RandomAmount="true"`
  * `minecraft:paper` (**Бумага**) | Кол-во: 4–8 | Вес: 75 | `RandomAmount="true"`
  * `minecraft:book` (**Книга**) | Кол-во: 1 | Вес: 65 | `RandomAmount="false"`
  * `modernmarkings:item.chalk` (**Мел**) | Кол-во: 2–4 | Вес: 70 | `RandomAmount="true"`
  * `minecraft:compass` (**Компас**) | Кол-во: 1 | Вес: 55 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `minecraft:string` (**Нить**) | Кол-во: 4–8 | Вес: 70 | `RandomAmount="true"`
  * `minecraft:iron_bars` (**Железные прутья**) | Кол-во: 3–6 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.fuse` (**Предохранитель**) | Кол-во: 1–2 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.siren_track` (**Запись сирены**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `minecraft:tripwire_hook` (**Растяжка**) | Кол-во: 1–2 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.padlock` (**Замок**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `minecraft:sign` (**Табличка**) | Кол-во: 1–3 | Вес: 60 | `RandomAmount="true"`
  * `OpenBlocks:generic:5` (**Шнур**) | Кол-во: 2–4 | Вес: 55 | `RandomAmount="true"`

#### 📻 [Meta 251] Коробочка радиодеталей
* **Уровень:** 2 | **Rarity:** 1 | **Дроп:** `2 - 3` | `CombineTrashGroup="true"`
* *Лор:* Коробочка радиолюбителя или техника узла связи: лампы, конденсаторы, аналоговые микросхемы, кварц, ферриты, индуктивности, карманный кассетник и радио-пейджер. Никаких тяжёлых промышленных аккумуляторов.
* **Содержимое:**
  * `hbm:item.rtty_pager` (**RTTY Пейджер**) | Кол-во: 1 | Вес: 50 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Беспроводной приём цифровых радиокоманд.
  * `openfm:RadioTuner` (**Radio Tuner**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Поиск радиостанций OpenFM.
  * `openfm:MemoryCard` (**Карта памяти OpenFM**) | Кол-во: 1 | Вес: 50 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `computronics:computronics.portableTapeDrive` (**Кассетный плеер**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Воспроизведение аудиокассет на ходу.
  * `computronics:computronics.tape:0` (**Железная кассета**) | Кол-во: 1 | Вес: 60 | `RandomAmount="false"`
  * `computronics:computronics.tape:1` (**Золотая кассета**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `computronics:computronics.parts:0` (**Магнитная головка**) | Кол-во: 1 | Вес: 50 | `RandomAmount="false"`
  * `hbm:item.detonator` (**Детонатор**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:tile.radio_torch_sender` (**Радиофакел-передатчик**) | Кол-во: 1 | Вес: 50 | `RandomAmount="true"`
  * `hbm:tile.radio_torch_receiver` (**Радиофакел-приёмник**) | Кол-во: 1 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.circuit:0` (**Вакуумная лампа**) | Кол-во: 2–4 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.circuit:1` (**Конденсатор**) | Кол-во: 1–3 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.circuit:2` (**Аналоговая плата**) | Кол-во: 1–2 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.wire_fine:2900` (**Медный провод**) | Кол-во: 6–12 | Вес: 75 | `RandomAmount="true"`
  * `ProjRed|Core:projectred.core.part:16` (**Индукционная катушка**) | Кол-во: 1–3 | Вес: 55 | `RandomAmount="true"`
  * `ProjRed|Core:projectred.core.part:17` (**Ферритовый сердечник**) | Кол-во: 1–3 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.fuse` (**Предохранитель**) | Кол-во: 1–3 | Вес: 60 | `RandomAmount="true"`
  * `minecraft:quartz` (**Кварц**) | Кол-во: 3–6 | Вес: 70 | `RandomAmount="true"`. *Смысл:* Кварцевый стабилизатор частоты.
  * `minecraft:iron_bars` (**Железные прутья**) | Кол-во: 2–4 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.power_net_tool` (**Тестер электросети**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `minecraft:redstone` (**Красная пыль**) | Кол-во: 6–12 | Вес: 70 | `RandomAmount="true"`

#### 📡 [Meta 252] Радиоузел связиста
* **Уровень:** 3 | **Rarity:** 2 | **Дроп:** `2 - 4` | `CombineTrashGroup="false"`
* *Лор:* Блок координации ПВО, шифрования и радарного наведения командного бункера.
* **Содержимое:**
  * `hbm:item.radar_linker` (**Радарный соединитель**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Привязка радаров к пусковым установкам.
  * `computronics:computronics.tape:5` (**CDVR кассета данных**) | Кол-во: 1 | Вес: 30 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `computronics:computronics.tape:2` (**Алмазная кассета**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `OpenComputers:item:19` (**Металлический диск**) | Кол-во: 2–4 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.circuit:21` (**Атомные часы**) | Кол-во: 1 | Вес: 20 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.circuit:14` (**Плата авионики**) | Кол-во: 1–2 | Вес: 35 | `RandomAmount="true"`
  * `hbm:item.circuit:10` (**Военная схема**) | Кол-во: 1–2 | Вес: 40 | `RandomAmount="true"`
  * `hbm:item.circuit:15` (**Корпус блока управления**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.hard_drive` (**Жёсткий диск**) | Кол-во: 1 | Вес: 30 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.wire_fine:38` (**Сверхпроводник**) | Кол-во: 2–4 | Вес: 35 | `RandomAmount="true"`
  * `hbm:item.wire_fine:7900` (**Золотой провод**) | Кол-во: 3–6 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.pa_coil` (**PA-катушка**) | Кол-во: 1 | Вес: 30 | `RandomAmount="false"`
  * `hbm:item.magnetron` (**Магнетрон**) | Кол-во: 1–2 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.coil_gold_torus` (**Тороид**) | Кол-во: 1–3 | Вес: 35 | `RandomAmount="true"`
  * `hbm:item.battery_pack:3` (**Энергоячейка**) | Кол-во: 1 | Вес: 30 | `RandomAmount="false"`
  * `hbm:item.plate_mixed` (**Пластина сплава**) | Кол-во: 1–2 | Вес: 35 | `RandomAmount="true"`
  * `hbm:item.launch_code_piece` (**Фрагмент кода запуска**) | Кол-во: 1 | Вес: 15 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.rtty_pager` (**RTTY Пейджер**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `computronics:computronics.parts:0` (**Магнитная головка**) | Кол-во: 1–2 | Вес: 35 | `RandomAmount="true"`

---

### Блок 280–309: Химия, полимеры и лабораторная посуда

#### 🧪 [Meta 280] Сумка лаборанта
* **Уровень:** 1 | **Rarity:** 0 | **Дроп:** `1 - 2` | `CombineTrashGroup="true"`
* *Лор:* Походный кейс лаборанта полевого анализа и синтеза полимеров.
* **Содержимое:**
  * `hbm:item.pipette_laboratory` (**Лабораторная пипетка 50 mB**) | Кол-во: 1 | Вес: 80 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `exnihilo:porcelain` (**Фарфоровая глина**) | Кол-во: 3–6 | Вес: 80 | `RandomAmount="true"`
  * `hbm:item.ball_resin` (**Латекс/Смола**) | Кол-во: 2–4 | Вес: 70 | `RandomAmount="true"`
  * `hbm:item.ingot_biorubber` (**Брусок биорезины**) | Кол-во: 1–3 | Вес: 60 | `RandomAmount="true"`
  * `minecraft:glass_bottle` (**Колба**) | Кол-во: 1–3 | Вес: 70 | `RandomAmount="true"`
  * `minecraft:clay_ball` (**Глина**) | Кол-во: 3–6 | Вес: 70 | `RandomAmount="true"`
  * `minecraft:potion` (**Бутылка с водой**) | Кол-во: 1 | Вес: 75 | `RandomAmount="false"`
  * `hbm:item.powder_calcium` (**Карбонат кальция**) | Кол-во: 3–6 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.powder_coal` (**Угольный порошок**) | Кол-во: 4–8 | Вес: 65 | `RandomAmount="true"`
  * `harvestcraft:saltItem` (**Соль**) | Кол-во: 4–8 | Вес: 70 | `RandomAmount="true"`
  * `hbm:item.sulfur` (**Сера**) | Кол-во: 4–8 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.niter` (**Селитра**) | Кол-во: 4–8 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.fluorite` (**Флюорит**) | Кол-во: 4–8 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.oil_tar` (**Каменноугольный деготь**) | Кол-во: 2–4 | Вес: 55 | `RandomAmount="true"`
  * `hbm:item.pads_rubber` (**Резиновые прокладки**) | Кол-во: 2–4 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.plastic_bag` (**Пакет**) | Кол-во: 1–2 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.goggles` (**Защитные очки**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `cfm:ItemSoap` (**Мыло**) | Кол-во: 1 | Вес: 45 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.debris_metal` (**Металлолом**) | Кол-во: 1–2 | Вес: 50 | `RandomAmount="true"`

#### 🔬 [Meta 281] Кейс технолога
* **Уровень:** 2 | **Rarity:** 1 | **Дроп:** `2 - 4` | `CombineTrashGroup="true"`
* *Лор:* Комплект инженера-технолога нефтехимического комбината.
* **Содержимое:**
  * `hbm:item.pipette_boron` (**Борная пипетка 1000 mB**) | Кол-во: 1 | Вес: 60 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.siphon` (**Сифон**) | Кол-во: 1 | Вес: 50 | `LimitedDropCount="1"` | `RandomAmount="false"`. *Смысл:* Ручной перекачивающий насос для чанов.
  * `hbm:item.bottle_mercury` (**Ртуть**) | Кол-во: 1 | Вес: 50 | `RandomAmount="false"`
  * `hbm:item.stick_pvc` (**ПВХ стержень**) | Кол-во: 2–4 | Вес: 70 | `RandomAmount="true"`
  * `hbm:item.stick_vinyl` (**Виниловый стержень**) | Кол-во: 2–4 | Вес: 70 | `RandomAmount="true"`
  * `hbm:item.oil_tar:3` (**Парафин**) | Кол-во: 4–8 | Вес: 70 | `RandomAmount="true"`
  * `hbm:item.oil_tar:4` (**Хлорированный воск**) | Кол-во: 4–8 | Вес: 70 | `RandomAmount="true"`
  * `hbm:item.ingot_bakelite` (**Бакелит**) | Кол-во: 2–4 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.ingot_polymer` (**Полимерный слиток**) | Кол-во: 2–4 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.ingot_asbestos` (**Асбест**) | Кол-во: 2–4 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.solid_fuel` (**Твёрдое топливо**) | Кол-во: 4–8 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.solid_fuel_presto` (**Топливо Presto**) | Кол-во: 2–4 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.rocket_fuel` (**Ракетное топливо**) | Кол-во: 3–6 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.bdcl` (**BDCL пластина**) | Кол-во: 1 | Вес: 45 | `RandomAmount="false"`
  * `hbm:item.filter_coal` (**Угольный фильтр**) | Кол-во: 1–2 | Вес: 55 | `RandomAmount="true"`
  * `hbm:item.canister_full:111` (**Канистра нефтепродуктов**) | Кол-во: 1–2 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.gas_mask_mono` (**Полумаска**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.powder_copper` (**Медный порошок**) | Кол-во: 3–6 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.powder_iron` (**Железный порошок**) | Кол-во: 3–6 | Вес: 60 | `RandomAmount="true"`
  * `hbm:item.catalytic_converter` (**Катализатор**) | Кол-во: 1 | Вес: 25 | `LimitedDropCount="1"` | `RandomAmount="false"`

---

### Блок 310–339: Астронавтика и упавший челнок (`POOL_SPACESHIP`)

#### 🛰️ [Meta 310] Чёрный ящик шаттла
* **Уровень:** 3 | **Rarity:** 3 (Epic) | **Дроп:** `2 - 4` | `CombineTrashGroup="false"`
* *Лор:* Несгораемый аварийный регистратор телеметрии челнока.
* **Содержимое:**
  * `hbm:item.circuit:14` (**Плата авионики**) | Кол-во: 1–2 | Вес: 40 | `RandomAmount="true"`
  * `computronics:computronics.tape:5` (**Межгалактический CDVR**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.hard_drive` (**Ударопрочный жёсткий диск**) | Кол-во: 1 | Вес: 30 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `OpenComputers:item:19` (**Металлический диск**) | Кол-во: 2–4 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.circuit:21` (**Атомные часы**) | Кол-во: 1 | Вес: 20 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.circuit:15` (**Корпус блока управления**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.circuit:10` (**Военная микросхема**) | Кол-во: 1–2 | Вес: 40 | `RandomAmount="true"`
  * `hbm:item.circuit:16` (**Интерфейсная суперплата**) | Кол-во: 1 | Вес: 25 | `RandomAmount="false"`
  * `hbm:item.crt_display` (**ЭЛТ-дисплей**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.radar_linker` (**Радарный соединитель**) | Кол-во: 1 | Вес: 30 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.wire_fine:7900` (**Золотой провод**) | Кол-во: 4–8 | Вес: 55 | `RandomAmount="true"`
  * `hbm:item.wire_fine:38` (**Сверхпроводящий кабель**) | Кол-во: 2–4 | Вес: 35 | `RandomAmount="true"`
  * `hbm:item.battery_pack:3` (**Энергетическая ячейка**) | Кол-во: 1 | Вес: 30 | `RandomAmount="false"`
  * `hbm:item.plate_titanium` (**Титановая пластина**) | Кол-во: 2–4 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.launch_code_piece` (**Фрагмент кода запуска**) | Кол-во: 1 | Вес: 20 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.photo_panel` (**Фотоэлемент**) | Кол-во: 2–4 | Вес: 40 | `RandomAmount="true"`
  * `hbm:item.magnetron` (**Магнетрон**) | Кол-во: 1 | Вес: 35 | `RandomAmount="false"`
  * `hbm:item.atmosphere_scanner` (**Анализатор атмосферы**) | Кол-во: 1 | Вес: 30 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.billet_silicon` (**Кремниевая подложка**) | Кол-во: 4–8 | Вес: 50 | `RandomAmount="true"`

#### 🚀 [Meta 311] Транспортный пенал изотопов
* **Уровень:** 3 | **Rarity:** 3 (Epic) | **Дроп:** `1 - 3` | `CombineTrashGroup="false"`
* *Лор:* Тяжёлый радиационно-защищённый пенал транспортировки делящихся материалов и термоядерного топлива челнока.
* **Содержимое:**
  * `hbm:item.cell_deuterium` (**Пробирка с дейтерием**) | Кол-во: 1–4 | Вес: 60 | `RandomAmount="true"`. *Смысл:* Термоядерное топливо.
  * `hbm:item.cell_tritium` (**Пробирка с тритием**) | Кол-во: 1–4 | Вес: 60 | `RandomAmount="true"`. *Смысл:* Изотоп зажигания плазмы.
  * `hbm:item.cell_antimatter` (**Пробирка с антиматерией**) | Кол-во: 1 | Вес: 10 | `LimitedDropCount="1"` | `RandomAmount="false"`. `[Маяк прогресса]` (~1.4% шанс на ролл).
  * `hbm:item.solid_fuel_presto_triplet` (**Топливные поленья**) | Кол-во: 2–4 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.cell_empty` (**Пустая ячейка**) | Кол-во: 3–6 | Вес: 70 | `RandomAmount="true"`
  * `hbm:item.pellet_rtg_americium` (**Америциевая РИТЭГ-таблетка**) | Кол-во: 1 | Вес: 25 | `LimitedDropCount="2"` | `RandomAmount="false"`
  * `hbm:item.pellet_rtg_radium` (**Радиевая РИТЭГ-таблетка**) | Кол-во: 1 | Вес: 30 | `LimitedDropCount="2"` | `RandomAmount="false"`
  * `hbm:item.ingot_u235` (**Уран-235**) | Кол-во: 1–2 | Вес: 35 | `RandomAmount="true"`
  * `hbm:item.ingot_u238` (**Уран-238**) | Кол-во: 3–6 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.ingot_pu238` (**Плутоний-238**) | Кол-во: 1–2 | Вес: 30 | `RandomAmount="true"`
  * `hbm:item.ingot_am241` (**Америций-241**) | Кол-во: 1–2 | Вес: 30 | `RandomAmount="true"`
  * `hbm:item.ingot_lead` (**Свинцовый слиток**) | Кол-во: 4–8 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.plate_lead` (**Свинцовая пластина**) | Кол-во: 3–6 | Вес: 65 | `RandomAmount="true"`
  * `hbm:item.hazmat_cloth_grey` (**Ткань химзащиты**) | Кол-во: 2–4 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.dosimeter` (**Дозиметр**) | Кол-во: 1 | Вес: 40 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.radx` (**Рад-X**) | Кол-во: 1–3 | Вес: 55 | `RandomAmount="true"`
  * `hbm:item.cell_puf6` (**Ячейка с PUF6**) | Кол-во: 1 | Вес: 20 | `RandomAmount="false"`
  * `hbm:item.cell_uf6` (**Ячейка с UF6**) | Кол-во: 1 | Вес: 25 | `RandomAmount="false"`

#### 🛡️ [Meta 312] Бортовой ремнабор
* **Уровень:** 3 | **Rarity:** 2 (Rare) | **Дроп:** `2 - 4` | `CombineTrashGroup="false"`
* *Лор:* Аварийный ЗИП шлюзового отсека для экстренной заделки пробоин обшивки шаттла и ремонта в открытом космосе.
* **Содержимое:**
  * `hbm:tile.block_tungsten` (**Вольфрамовый блок**) | Кол-во: 1–2 | Вес: 40 | `RandomAmount="true"`. *Смысл:* Сверхтугоплавкий тепловой щит.
  * `hbm:item.wire_dense:31` (**Плотный кабель 31**) | Кол-во: 2–4 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.powder_niobium` (**Ниобиевый порошок**) | Кол-во: 1–2 | Вес: 30 | `RandomAmount="true"`
  * `hbm:item.powder_neodymium` (**Неодимовый порошок**) | Кол-во: 1–2 | Вес: 30 | `RandomAmount="true"`
  * `hbm:item.plate_titanium` (**Титановая пластина**) | Кол-во: 4–8 | Вес: 70 | `RandomAmount="true"`
  * `hbm:item.plate_saturnite` (**Пластина сатурнита**) | Кол-во: 2–4 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.plate_mixed` (**Улучшенный сплав**) | Кол-во: 1–3 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.plate_armor_titanium` (**Титановая бронеплита**) | Кол-во: 2–4 | Вес: 50 | `RandomAmount="true"`
  * `hbm:item.plate_armor_lunar` (**Лунная бронеплита**) | Кол-во: 2–4 | Вес: 40 | `RandomAmount="true"`
  * `hbm:item.ingot_desh` (**Слиток деша**) | Кол-во: 2–4 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.plate_desh` (**Пластина деша**) | Кол-во: 1–3 | Вес: 45 | `RandomAmount="true"`
  * `hbm:item.motor_desh` (**Деш-мотор**) | Кол-во: 1–2 | Вес: 40 | `RandomAmount="true"`
  * `hbm:item.blade_tungsten` (**Вольфрамовые лезвия**) | Кол-во: 1 | Вес: 35 | `RandomAmount="false"`
  * `hbm:item.blade_titanium` (**Титановые лезвия**) | Кол-во: 1 | Вес: 35 | `RandomAmount="false"`
  * `hbm:item.wrench_archineer` (**Гаечный ключ инженера**) | Кол-во: 1 | Вес: 30 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.drill_titanium` (**Титановый бур**) | Кол-во: 1 | Вес: 35 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.reacher` (**Вольфрамовые хваталки**) | Кол-во: 1 | Вес: 30 | `LimitedDropCount="1"` | `RandomAmount="false"`
  * `hbm:item.pipes_steel` (**Стальные трубы**) | Кол-во: 1 | Вес: 35 | `RandomAmount="false"`
  * `hbm:item.cladding_desh` (**Деш-оболочка**) | Кол-во: 1–2 | Вес: 35 | `RandomAmount="true"`
  * `hbm:item.tank_steel` (**Стальной резервуар**) | Кол-во: 3–6 | Вес: 45 | `RandomAmount="true"`

---

## 4. Карта привязки к структурам NTM (`_hbmItemPools.json`)

| Пул HBM (`_hbmItemPools.json`) | Назначение / Структура в мире | Интегрированные контейнеры (Meta ID) | Вес |
| :--- | :--- | :--- | :---: |
| **`POOL_SPACESHIP`** | Разбившийся звездолёт | Чёрный ящик шаттла (310), Транспортный пенал изотопов (311), Бортовой ремнабор (312) | 3 |
| **`POOL_VERTIBIRD`** | Упавший винтокрыл | Патронный цинк (101), Коробочка радиодеталей (251), Цеховая аптечка (11) | 4 |
| **`POOL_VAULT_LAB`** | Лабораторное хранилище | Инженерный ЗИП (42), Потрёпанный блок стойки ЭВМ (72), Кейс технолога (281), Банк семян (162) | 2 |
| **`POOL_VAULT_REINFORCED`** | Укреплённый бункер | Кейс РХБЗ (12), Оружейный кофр (102), Кейс проектировщика (192) | 1 |
| **`POOL_VAULT_STANDARD`** | Стандартный бункер | Цеховая аптечка (11), Сумка механика (41), Патронный цинк (101), Офицерский паёк (132) | 1 |
| **`POOL_SILO`** | Ракетная шахта | Оружейный кофр (102), Сумка электрика (71), Кейс РХБЗ (12) | 2 |
| **`POOL_OIL_RIG`** | Нефтяная вышка | Геодезический набор (221), Кейс технолога (281), Сумка механика (41), Походный кухонник (131) | 2 |
| **`POOL_SUBMARINE`** | Затонувшая подлодка | Кейс РХБЗ (12), Коробочка радиодеталей (251), Сумка механика (41) | 2 |
| **`POOL_MACHINE_PARTS`** | Заводские цеха и станки | Ящик слесаря (40), Сумка механика (41), Сумка маляра (191) | 2–3 |
| **`POOL_OFFICE_TRASH`** | Офисный мусор и корзины | Рваная аптечка (10), Скрутка проводов (70), Планшет разведчика (220), Сумка сигнальщика (250) | 2 |
| **`POOL_FILING_CABINET`** | Картотечные шкафы контор | Рваная аптечка (10), Скрутка проводов (70), Планшет разведчика (220), Сумка сигнальщика (250) | 20 |
| **`POOL_SUPPLIES`** | Ящики снабжения | Котелок бродяги (130), Семена с огорода (160), Посевной мешок (161), Рваная аптечка (10) | 3–4 |
| **`POOL_SNACKS`** | Автоматы с едой и кухни | Котелок бродяги (130), Походный кухонник (131) | 2–3 |
| **`POOL_WEAPONS`** | Оружейные пирамиды | Старый подсумок (100), Патронный цинк (101) | 15–25 |
| **`POOL_AMMO`** | Армейские боеприпасы | Старый подсумок (100), Патронный цинк (101) | 3–5 |
| **`POOL_GENERIC`** | Базовые тайники заброшек | Рваная аптечка (10), Ящик слесаря (40), Скрутка проводов (70), Котелок бродяги (130), Семена с огорода (160), Мешок плотника (190), Планшет разведчика (220), Сумка сигнальщика (250), Сумка лаборанта (280) | 2–3 |

---

## 5. Архитектура локализации

1. Русские названия мешков берутся напрямую из атрибута `GroupName="..."` файла `config/EnhancedLootBags/LootBags.xml`.
2. Мод форматирует отображаемое имя предмета через шаблон в языковом файле:
   * Путь загрузки TxLoader: `config/txloader/forceload/enhancedlootbags/lang/ru_RU.lang`
   * Путь ресурспака: `resourcepacks/NTNH-Modernity-2.1/assets/enhancedlootbags/lang/ru_RU.lang`
3. Строка конфигурации:
   ```properties
   enhancedlootbags.string.lootbag_templatename=%s
   ```
   Благодаря подстановке `%s` мешки отображаются в инвентаре чистым названием без технических префиксов (например, *«Патронный цинк»*, а не *«LootBag: Патронный цинк»*).

---

## 6. Чек-лист готовности
1. [x] Проверены реальные внутриигровые названия предметов по `.lang` файлам.
2. [x] Все механики сверены по байткоду классов Java.
3. [x] Архитектура переведена на диапазоны по 30 ID.
4. [x] Выделены отдельные группы: «Агрономия и семена», «Геологоразведка».
5. [x] Названия избавлены от канцелярита и сокращены до ёмких и живых.
6. [x] Генерация XML-конфигурации `config/EnhancedLootBags/LootBags.xml`.
7. [x] Привязка пулов в `config/hbmConfig/_hbmItemPools.json` (57 вхождений в 16 пулов структур).
8. [x] Настройка локализации и чистого отображения названий без префиксов через TxLoader (`config/txloader/forceload/enhancedlootbags/lang/`).
9. [x] Удалены устаревшие легаси-группы (1–6, 999), сохранён чистый пул из 32 тематических групп и технической группы хлама.

---

## 7. Политика баланса: Одноразовое снаряжение vs Расходники

### Проблема «хлама дубликатов»
Инструменты, гаечные ключи, сканеры, дозиметры, противогазы и книги — это предметы «одноразовой ценности». Получив их однажды, игрок закрывает потребность, а повторные дропы захламляют сундуки.

### 4 категории наполнителей
1. **Уникальное снаряжение и приборы (`LimitedDropCount="1"` или `"2"`):**
   * Все постоянные инструменты (разводные ключи, отвёртки, отладчики сетей, ножовки, нож повара, стамески Chisel, архитектурные молотки).
   * Приборы и СИЗ (противогазы M65/моно, счётчики Гейгера, дозиметры, сканеры плотности, детекторы нефти, спектрометры).
   * Очки, каталоги, книги рецептов и планшеты.
   * *Вес и вероятность:* Вес позиций составляет 25–50, что при типичной сумме весов группы ~350–500 соответствует **~8–12% вероятности на ролл**.
   * *Механика:* Мод Enhanced LootBags фиксирует выпадение в NBT/файле `LootBags.dat` мира. По достижении лимита предмет исключается из будущих роллов для игрока.
2. **«Маяки прогресса» и технологические тизеры (Вес 10–25, ~1.4–5% на ролл, без лимита):**
   * Редкие компоненты старших технологических эпох (авионика, антиматерия, тритий, сверхпроводники, титановые бронеплиты, зашифрованные кассеты данных).
   * *Назначение:* Служат исследовательским триггером. Игрок видит в NEI грандиозные технологические деревья и ставит долгосрочные производственные цели.
3. **Востребованные расходники и крафт-компоненты (70–75% объёма, шанс 50–85%):**
   * Провода всех калибров (медные, изолированные, силовые, сверхпроводники).
   * Радиодетали (лампы, конденсаторы, ферриты, печатные платы).
   * Металлургические полуфабрикаты и пластины (железо, свинец, сталь, титан, кевлар).
   * Боеприпасы (9mm, 5.56, 7.62, .50 BMG, картечь, гранаты 40mm, гильзы, кордит).
   * Фармацевтика (фильтры противогазов, стимуляторы, Med-X, Rad-X, йод, хлопок).
4. **Тематический атмосферный лом (15–20% объёма, шанс 25–45%):**
   * Обрезки проводов, стреляные гильзы, ветошь, галька, битый фарфор, шрапнель. Пригоден для вторичной переплавки или крафта примитивных патронов.

### 📝 Заметка на будущее: Разбор снаряжения (Dismantling & Salvaging)
Запланировано добавление рецептов утилизации дубликатов приборов и масок в дробителях HBM на резину, чипы, текстолит и металлолом.

---

## 8. Аудит внешней LLM и принятые решения

| Тезис аудита | Утверждённое решение в NTNH |
| :--- | :--- |
| **1. «Выдача ценных ресурсов на старте ломает прогрессию»** | Введена концепция «Маяков прогресса»: хай-тек оставлен с минимальным шансом (до 5% на ролл, строго по 1 шт.). Без фабричной инфраструктуры предмет бесполезен, но стимулирует интерес к исследованию. |
| **2. «Инструменты и приборы забивают сундуки дубликатами»** | Реализована система `LimitedDropCount="1"` (или `"2"`), навсегда отсекающая повторные выпадения постоянных приборов и инструментов для конкретного игрока. |
| **3. «Тяжёлое ракетное вооружение не подходит лутбекам»** | Из пулов полностью исключены ракеты и артиллерийские выстрелы; оставлены только тактические пехотные боеприпасы и патронные комплекты. |
| **4. «Необходимость атмосферного тематического мусора»** | Используются исключительно существующие ID предметов сборки (`rag`, `dust_tiny`, `debris_metal`, `debris_shrapnel`, `casing`, `pin`), пригодные для переработки. |
| **5. «Удаление устаревших легаси-контейнеров»** | Легаси-группы 1–6 (LV1–LV4, AE2) и 999 удалены из конфигурации `LootBags.xml`. В игре работают строго 32 тематические группы и техническая группа Trash. |

---

## 9. Сводный реестр всех 33 групп в `LootBags.xml`

| ID (Meta) | Название контейнера / Группы | Уровень (Tier) | Rarity | Кол-во предметов (Min–Max) | Примесь хлама группы 0 (`CombineTrashGroup`) | Кол-во позиций лута в XML |
| :---: | :--- | :---: | :---: | :---: | :---: | :---: |
| **0** | Trash (Технический пул хлама) | — | 0 | 1 | false | 6 |
| **10** | Рваная аптечка | 1 | 0 | 1–2 | true | 20 |
| **11** | Цеховая аптечка | 2 | 1 | 2–3 | true | 20 |
| **12** | Кейс РХБЗ | 3 | 2 | 3–5 | false | 20 |
| **40** | Ящик слесаря | 1 | 0 | 1–2 | true | 19 |
| **41** | Сумка механика | 2 | 1 | 2–3 | true | 20 |
| **42** | Инженерный ЗИП | 3 | 2 | 3–4 | false | 20 |
| **70** | Скрутка проводов | 1 | 0 | 2–3 | true | 19 |
| **71** | Сумка электрика | 2 | 1 | 2–4 | true | 21 |
| **72** | Потрёпанный блок стойки ЭВМ | 3 | 2 | 3–5 | false | 21 |
| **100** | Вскрытый патронный цинк | 1 | 0 | 2–3 | true | 19 |
| **101** | Запечатанный патронный цинк | 2 | 1 | 1 (моно-навал) | false | 22 |
| **102** | Оружейный кофр | 3 | 2 | 2–4 | false | 19 |
| **130** | Котелок бродяги | 1 | 0 | 1–3 | true | 19 |
| **131** | Походный кухонник | 2 | 1 | 2–3 | true | 20 |
| **132** | Офицерский паёк | 3 | 2 | 3–4 | false | 19 |
| **160** | Семена с огорода | 1 | 0 | 2–3 | true | 19 |
| **161** | Посевной мешок | 2 | 1 | 2–4 | true | 20 |
| **162** | Банк семян | 3 | 2 | 1 (опт x32) | false | 20 |
| **190** | Мешок плотника | 1 | 0 | 2–4 | true | 24 |
| **191** | Сумка маляра | 2 | 1 | 2–4 | true | 34 |
| **192** | Кейс проектировщика | 3 | 2 | 3–5 | false | 21 |
| **220** | Планшет разведчика | 1 | 0 | 1–2 | true | 19 |
| **221** | Геодезический набор | 2 | 1 | 2–3 | true | 20 |
| **222** | Спектрометр недр | 3 | 2 | 2–4 | false | 19 |
| **250** | Сумка сигнальщика | 1 | 0 | 2–3 | true | 20 |
| **251** | Коробочка радиодеталей | 2 | 1 | 2–3 | true | 21 |
| **252** | Радиоузел связиста | 3 | 2 | 2–4 | false | 19 |
| **280** | Сумка лаборанта | 1 | 0 | 1–2 | true | 19 |
| **281** | Кейс технолога | 2 | 1 | 2–4 | true | 20 |
| **310** | Чёрный ящик шаттла | 3 | 3 | 2–4 | false | 19 |
| **311** | Транспортный пенал изотопов | 3 | 3 | 1–3 | false | 18 |
| **312** | Бортовой ремнабор | 3 | 2 | 2–4 | false | 20 |
| **ИТОГО** | **33 группы** | — | — | — | — | **656 позиций** |

---

## 10. Заметки по интеграции и интерфейсу (Backlog / Перспективы)

### 10.1. Постоянные лор-описания (тултипы) через MineTweaker
* **Концепция:** Перенести готовый художественный лор каждого мешка (из раздела 3) непосредственно в тултип предмета в инвентаре игрока.
* **Техническая реализация:** Скрипт `scripts/lootbags.zs` через стандартный API MineTweaker 3:
  ```zenscript
  // Пример для Рваной аптечки (meta: 10):
  <enhancedlootbags:lootbag:10>.addTooltip(format.italic(format.gray("Потрёпанная санитарная сумка с базовыми средствами первой помощи: бинты, антисептик и морфин.")));
  ```
* **Статус:** Зафиксировано в бэклоге как утверждённый вариант расширения. Отложено до этапа полировки UI/UX.

### 10.2. Скрытие рецептов / содержимого в NEI
* **Проблема:** В NEI по клавишам `U` (Использование) на сумке или `R` (Рецепт) на любом выпадающем предмете отображается полный список лута с точными процентами шансов. Это раскрывает все секреты и лишает игрока ощущения интриги/азарта при открытии.
* **Техническое решение:** В конфигурационном файле GTNH NEI `config/NEI/hiddenhandlers.cfg` регистрация обработчика `enhancedlootbags` полностью отключает вкладку рецептов содержимого сумок, сохраняя их механику как загадочные «чёрные ящики».
