DROP DATABASE IF EXISTS asm02;

CREATE DATABASE asm02;

USE asm02;


CREATE TABLE user (
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    address VARCHAR(255),
    description TEXT,
    email VARCHAR(255) NOT NULL UNIQUE,
    full_name VARCHAR(255) NOT NULL,
    avatar VARCHAR(255),
    phone VARCHAR(13),
    password VARCHAR(255) NOT NULL,
    role ENUM('JOB_SEEKER', 'RECRUITER') NOT NULL DEFAULT 'JOB_SEEKER',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted_at DATETIME DEFAULT NULL
);

CREATE TABLE cv (
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    file_name_on_server VARCHAR(255) NOT NULL UNIQUE COMMENT '<uuid>.pdf',
    user_id INT(11) NOT NULL,
    is_default BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE company (
    id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(13),
    description TEXT,
    logo VARCHAR(255),
    recruiter_id INT(11) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted_at DATETIME DEFAULT NULL,
    FOREIGN KEY (recruiter_id) REFERENCES user (id)
);

CREATE TABLE job_category (
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE job_post (
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    yoe VARCHAR(255) NOT NULL,
    vacancies TINYINT,
    address VARCHAR(255) NOT NULL,
    application_deadline DATE NOT NULL,
    salary_range VARCHAR(255) NOT NULL,
    created_at DATE NOT NULL DEFAULT(CURRENT_DATE),
    deleted_at DATE DEFAULT NULL,
    type ENUM(
        'FULL_TIME',
        'PART_TIME',
        'FREELANCE'
    ) NOT NULL DEFAULT 'FULL_TIME',
    category_id INT(11) NOT NULL,
    company_id INT(11) NOT NULL,
    Foreign Key (category_id) REFERENCES job_category (id),
    Foreign Key (company_id) REFERENCES company (id)
);

INSERT INTO
    user (
        email,
        full_name,
        password,
        role,
        phone
    )
VALUES (
        "nguoitimviec01@user",
        "Người tìm việc 01",
        "123",
        "JOB_SEEKER",
        "0912345678"
    ),
    (
        "nguoitimviec02@user",
        "Người tìm việc 02",
        "123",
        "JOB_SEEKER",
        "0912345679"
    ),
    (
        "nguoitimviec03@user",
        "Người tìm việc 03",
        "123",
        "JOB_SEEKER",
        "0912345680"
    ),
    (
        "nguoitimviec04@user",
        "Người tìm việc 04",
        "123",
        "JOB_SEEKER",
        "0912345681"
    ),
    (
        "nguoitimviec05@user",
        "Người tìm việc 05",
        "123",
        "JOB_SEEKER",
        "0912345682"
    ),
    (
        "nguoitimviec06@user",
        "Người tìm việc 06",
        "123",
        "JOB_SEEKER",
        "0912345683"
    ),
    (
        "nguoitimviec07@user",
        "Người tìm việc 07",
        "123",
        "JOB_SEEKER",
        "0912345684"
    ),
    (
        "nguoitimviec08@user",
        "Người tìm việc 08",
        "123",
        "JOB_SEEKER",
        "0912345685"
    ),
    (
        "nguoitimviec09@user",
        "Người tìm việc 09",
        "123",
        "JOB_SEEKER",
        "0912345686"
    ),
    (
        "nguoitimviec10@user",
        "Người tìm việc 10",
        "123",
        "JOB_SEEKER",
        "0912345687"
    ),
    (
        "recruiter01@user",
        "Nhà tuyển dụng 01",
        "123",
        "RECRUITER",
        "0902345678"
    ),
    (
        "recruiter02@user",
        "Nhà tuyển dụng 02",
        "123",
        "RECRUITER",
        "0902345679"
    ),
    (
        "recruiter03@user",
        "Nhà tuyển dụng 03",
        "123",
        "RECRUITER",
        "0902345680"
    ),
    (
        "recruiter04@user",
        "Nhà tuyển dụng 04",
        "123",
        "RECRUITER",
        "0902345681"
    ),
    (
        "recruiter05@user",
        "Nhà tuyển dụng 05",
        "123",
        "RECRUITER",
        "0902345682"
    );

INSERT INTO
    company (
        name,
        address,
        email,
        phone,
        description,
        recruiter_id
    )
VALUES (
        "Công ty Tuyển dụng 01",
        "Hà Nội, Việt Nam",
        "recruiter01@company.com",
        "0902345678",
        "Công ty chuyên cung cấp dịch vụ tuyển dụng.",
        11
    ),
    (
        "Công ty Tuyển dụng 02",
        "Hồ Chí Minh, Việt Nam",
        "recruiter02@company.com",
        "0902345679",
        "Cung cấp các giải pháp nhân sự cho doanh nghiệp.",
        12
    ),
    (
        "Công ty Tuyển dụng 03",
        "Đà Nẵng, Việt Nam",
        "recruiter03@company.com",
        "0902345680",
        "Tuyển dụng chất lượng cao cho các công ty lớn.",
        13
    ),
    (
        "Công ty Tuyển dụng 04",
        "Hải Phòng, Việt Nam",
        "recruiter04@company.com",
        "0902345681",
        "Đồng hành cùng doanh nghiệp trong việc tuyển dụng nhân sự.",
        14
    ),
    (
        "Công ty Tuyển dụng 05",
        "Cần Thơ, Việt Nam",
        "recruiter05@company.com",
        "0902345682",
        "Giúp kết nối doanh nghiệp và ứng viên phù hợp.",
        15
    );

INSERT INTO
    job_category (name)
VALUES ("NodeJs"),
    ("NestJs"),
    ("NodeJs/NextJs"),
    ("Python"),
    ("Python/Django"),
    ("JAVA"),
    ("JAVA/Spring"),
    ("C#"),
    ("C#/Unity"),
    (".NET"),
    ("PHP"),
    ("PHP/laravel"),
    ("React"),
    ("Vue"),
    ("Angular"),
    ("ServiceNow"),
    ("SaleForce"),
    ("RoR");

INSERT INTO
    job_post (
        title,
        description,
        yoe,
        vacancies,
        address,
        application_deadline,
        salary_range,
        type,
        category_id,
        company_id
    )
VALUES (
        "NodeJs Developer",
        "Develop and maintain NodeJs applications.",
        "2+ years",
        3,
        "Hà Nội, Việt Nam",
        "2024-12-31",
        "10,000,000 - 15,000,000 VND",
        "FULL_TIME",
        1,
        1
    ),
    (
        "React Developer",
        "Create modern and responsive web applications.",
        "1+ years",
        2,
        "Hà Nội, Việt Nam",
        "2024-12-15",
        "12,000,000 - 18,000,000 VND",
        "FULL_TIME",
        13,
        1
    ),
    (
        "Java Developer",
        "Build Java-based backend systems.",
        "3+ years",
        5,
        "Hà Nội, Việt Nam",
        "2024-11-30",
        "15,000,000 - 20,000,000 VND",
        "FULL_TIME",
        6,
        1
    ),
    (
        "PHP Developer",
        "Develop scalable web applications using PHP.",
        "2+ years",
        4,
        "Hà Nội, Việt Nam",
        "2024-12-25",
        "8,000,000 - 12,000,000 VND",
        "FULL_TIME",
        11,
        1
    ),
    (
        "QA Engineer",
        "Test and ensure quality of software products.",
        "1+ years",
        2,
        "Hà Nội, Việt Nam",
        "2024-12-10",
        "8,000,000 - 12,000,000 VND",
        "PART_TIME",
        17,
        1
    );

-- Job Posts for Công ty Tuyển dụng 02
INSERT INTO
    job_post (
        title,
        description,
        yoe,
        vacancies,
        address,
        application_deadline,
        salary_range,
        type,
        category_id,
        company_id
    )
VALUES (
        "NestJs Developer",
        "Work on backend development using NestJs.",
        "2+ years",
        3,
        "Hồ Chí Minh, Việt Nam",
        "2024-12-31",
        "12,000,000 - 18,000,000 VND",
        "FULL_TIME",
        2,
        2
    ),
    (
        "Vue Developer",
        "Build user interfaces with Vue.js.",
        "1+ years",
        2,
        "Hồ Chí Minh, Việt Nam",
        "2024-12-20",
        "10,000,000 - 15,000,000 VND",
        "FULL_TIME",
        14,
        2
    ),
    (
        "Java Developer",
        "Backend development with Java and Spring.",
        "3+ years",
        4,
        "Hồ Chí Minh, Việt Nam",
        "2024-11-30",
        "15,000,000 - 20,000,000 VND",
        "FULL_TIME",
        6,
        2
    ),
    (
        "C# Developer",
        "Work on applications using C# and .NET.",
        "2+ years",
        3,
        "Hồ Chí Minh, Việt Nam",
        "2024-12-25",
        "13,000,000 - 17,000,000 VND",
        "FULL_TIME",
        8,
        2
    ),
    (
        "Salesforce Developer",
        "Develop Salesforce applications and integrations.",
        "3+ years",
        2,
        "Hồ Chí Minh, Việt Nam",
        "2024-12-15",
        "18,000,000 - 25,000,000 VND",
        "FREELANCE",
        16,
        2
    );

-- Job Posts for Công ty Tuyển dụng 03
INSERT INTO
    job_post (
        title,
        description,
        yoe,
        vacancies,
        address,
        application_deadline,
        salary_range,
        type,
        category_id,
        company_id
    )
VALUES (
        "NodeJs Developer",
        "Design and develop backend services using NodeJs.",
        "2+ years",
        3,
        "Đà Nẵng, Việt Nam",
        "2024-12-31",
        "12,000,000 - 18,000,000 VND",
        "FULL_TIME",
        1,
        3
    ),
    (
        "Angular Developer",
        "Develop dynamic web applications with Angular.",
        "1+ years",
        2,
        "Đà Nẵng, Việt Nam",
        "2024-12-20",
        "10,000,000 - 14,000,000 VND",
        "PART_TIME",
        15,
        3
    ),
    (
        "Python Developer",
        "Develop applications using Python and Django.",
        "3+ years",
        5,
        "Đà Nẵng, Việt Nam",
        "2024-11-30",
        "14,000,000 - 20,000,000 VND",
        "FULL_TIME",
        4,
        3
    ),
    (
        "C#/Unity Developer",
        "Work on game development and applications using Unity.",
        "2+ years",
        4,
        "Đà Nẵng, Việt Nam",
        "2024-12-25",
        "12,000,000 - 18,000,000 VND",
        "FREELANCE",
        9,
        3
    ),
    (
        "RoR Developer",
        "Develop Ruby on Rails applications.",
        "1+ years",
        3,
        "Đà Nẵng, Việt Nam",
        "2024-12-10",
        "10,000,000 - 15,000,000 VND",
        "FULL_TIME",
        17,
        3
    );

-- Job Posts for Công ty Tuyển dụng 04
INSERT INTO
    job_post (
        title,
        description,
        yoe,
        vacancies,
        address,
        application_deadline,
        salary_range,
        type,
        category_id,
        company_id
    )
VALUES (
        "Python Developer",
        "Design and implement Python-based applications.",
        "2+ years",
        3,
        "Hải Phòng, Việt Nam",
        "2024-12-31",
        "12,000,000 - 18,000,000 VND",
        "FULL_TIME",
        5,
        4
    ),
    (
        "PHP/Laravel Developer",
        "Develop web applications using PHP and Laravel.",
        "3+ years",
        4,
        "Hải Phòng, Việt Nam",
        "2024-12-15",
        "10,000,000 - 15,000,000 VND",
        "FULL_TIME",
        12,
        4
    ),
    (
        "React Developer",
        "Create modern web applications using React.",
        "2+ years",
        5,
        "Hải Phòng, Việt Nam",
        "2024-11-30",
        "15,000,000 - 20,000,000 VND",
        "FULL_TIME",
        13,
        4
    ),
    (
        "ServiceNow Developer",
        "Work on the ServiceNow platform for business process automation.",
        "2+ years",
        3,
        "Hải Phòng, Việt Nam",
        "2024-12-25",
        "18,000,000 - 25,000,000 VND",
        "FREELANCE",
        15,
        4
    ),
    (
        "Java Developer",
        "Develop and maintain backend services using Java.",
        "3+ years",
        4,
        "Hải Phòng, Việt Nam",
        "2024-12-10",
        "16,000,000 - 22,000,000 VND",
        "FULL_TIME",
        6,
        4
    );

-- Job Posts for Công ty Tuyển dụng 05
INSERT INTO
    job_post (
        title,
        description,
        yoe,
        vacancies,
        address,
        application_deadline,
        salary_range,
        type,
        category_id,
        company_id
    )
VALUES (
        "Java/Spring Developer",
        "Backend development with Java and Spring.",
        "2+ years",
        4,
        "Cần Thơ, Việt Nam",
        "2024-12-31",
        "14,000,000 - 20,000,000 VND",
        "FULL_TIME",
        7,
        5
    ),
    (
        "Vue Developer",
        "Work on web front-end applications with Vue.js.",
        "1+ years",
        3,
        "Cần Thơ, Việt Nam",
        "2024-12-20",
        "10,000,000 - 15,000,000 VND",
        "FULL_TIME",
        14,
        5
    ),
    (
        "Angular Developer",
        "Build dynamic and scalable applications with Angular.",
        "2+ years",
        5,
        "Cần Thơ, Việt Nam",
        "2024-12-10",
        "12,000,000 - 18,000,000 VND",
        "FULL_TIME",
        15,
        5
    ),
    (
        "C# Developer",
        "Develop software applications using C#.",
        "3+ years",
        3,
        "Cần Thơ, Việt Nam",
        "2024-12-25",
        "13,000,000 - 18,000,000 VND",
        "FULL_TIME",
        8,
        5
    ),
    (
        "RoR Developer",
        "Build web applications using Ruby on Rails.",
        "2+ years",
        2,
        "Cần Thơ, Việt Nam",
        "2024-12-15",
        "12,000,000 - 17,000,000 VND",
        "PART_TIME",
        17,
        5
    );

CREATE TABLE application (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT(11) NOT NULL,
    cv_id INT(11) NOT NULL,
    job_id INT(11) NOT NULL,
    created_at DATE NOT NULL DEFAULT(CURRENT_DATE),
    status ENUM(
        'PENDING',
        'ACCEPTED',
        'REJECTED'
    ) DEFAULT('PENDING'),
    text VARCHAR(255) DEFAULT(''),
    Foreign Key (cv_id) REFERENCES cv (id),
    Foreign Key (user_id) REFERENCES user (id),
    Foreign Key (job_id) REFERENCES job_post (id)
);

CREATE TABLE follows (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT(11) NOT NULL,
    entity_type ENUM('COMPANY', 'JOB_POST') NOT NULL,
    entity_id INT(11) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    Foreign Key (user_id) REFERENCES user (id)
);

-- FUNC
DELIMITER $$

CREATE FUNCTION GET_RANGE_DELIMITER(salary_range VARCHAR(255))
RETURNS VARCHAR(10)
DETERMINISTIC
BEGIN
  DECLARE _delimiter VARCHAR(10) DEFAULT '';
  IF salary_range RLIKE '\\s*(-)\\s*' THEN
    SET _delimiter = '-';
  ELSEIF salary_range RLIKE '\\s*(~)\\s*' THEN
    SET _delimiter = '~';
  ELSEIF salary_range RLIKE '\\s*(to)\\s*' THEN
    SET _delimiter = 'to';
  ELSEIF salary_range RLIKE '\\s*(đến)\\s*' THEN
    SET _delimiter = 'đến';
  ELSE
    SET _delimiter = '';
  END IF;
  RETURN _delimiter;
END$$

CREATE DEFINER=`root`@`%` FUNCTION `ConvertToVND`(usd_salary DECIMAL(10, 2)) RETURNS decimal(15,2)
    DETERMINISTIC
BEGIN
    DECLARE vnd_salary DECIMAL(15, 2);
    DECLARE conversion_rate DECIMAL(10, 2) DEFAULT 23000;
    SET vnd_salary = usd_salary * conversion_rate;
    RETURN vnd_salary;
END$$

DELIMITER ;

DROP VIEW IF EXISTS JobsWithApproximateSalary;

CREATE VIEW JobsWithApproximateSalary AS
-- VIEWS
SELECT
    *,
    CASE
    -- When $ or USD, need to use ConvertToVND() procedure
        WHEN jp.salary_range REGEXP 'USD|\\$' THEN CASE
        -- When there is hyphen (-), to/đến etc, calculate the mean of the range
            WHEN GET_RANGE_DELIMITER (jp.salary_range) COLLATE utf8mb4_unicode_ci <> '' THEN ConvertToVND (
                -- Lower Bound
                (
                    CAST(
                        REGEXP_REPLACE(
                            SUBSTRING_INDEX(
                                jp.salary_range,
                                GET_RANGE_DELIMITER (jp.salary_range),
                                1
                            ),
                            '[^0-9]',
                            ''
                        ) AS UNSIGNED
                    ) + CAST(
                        -- Upper Bound
                        REGEXP_REPLACE(
                            SUBSTRING_INDEX(
                                jp.salary_range,
                                GET_RANGE_DELIMITER (jp.salary_range),
                                -1
                            ),
                            '[^0-9]',
                            ''
                        ) AS UNSIGNED
                    )
                ) / 2
            )
            ELSE ConvertToVND (
                CAST(
                    REGEXP_REPLACE(jp.salary_range, '[^0-9]', '') AS DECIMAL(10, 2)
                )
            )
        END
        -- When vnd
        WHEN jp.salary_range REGEXP 'đ|vnd|vnđ|d|₫' THEN CASE
            WHEN GET_RANGE_DELIMITER (jp.salary_range) COLLATE utf8mb4_unicode_ci <> '' THEN (
                CAST(
                    REGEXP_REPLACE(
                        SUBSTRING_INDEX(
                            jp.salary_range,
                            GET_RANGE_DELIMITER (jp.salary_range),
                            1
                        ),
                        '[^0-9]',
                        ''
                    ) AS UNSIGNED
                ) + CAST(
                    REGEXP_REPLACE(
                        SUBSTRING_INDEX(
                            jp.salary_range,
                            GET_RANGE_DELIMITER (jp.salary_range),
                            -1
                        ),
                        '[^0-9]',
                        ''
                    ) AS UNSIGNED
                )
            ) / 2
            ELSE CAST(
                REGEXP_REPLACE(jp.salary_range, '[^0-9]', '') AS DECIMAL(10, 2)
            )
        END
        -- WHEN VND but short form (tr/triệu)
        WHEN jp.salary_range REGEXP 'tr|triệu' THEN CASE
            WHEN GET_RANGE_DELIMITER (jp.salary_range) COLLATE utf8mb4_unicode_ci <> '' THEN (
                1000000 * CAST(
                    REGEXP_REPLACE(
                        SUBSTRING_INDEX(
                            jp.salary_range,
                            GET_RANGE_DELIMITER (jp.salary_range),
                            1
                        ),
                        '[^0-9]',
                        ''
                    ) AS UNSIGNED
                ) + CAST(
                    REGEXP_REPLACE(
                        SUBSTRING_INDEX(
                            jp.salary_range,
                            GET_RANGE_DELIMITER (jp.salary_range),
                            -1
                        ),
                        '[^0-9]',
                        ''
                    ) AS UNSIGNED
                )
            ) / 2
            ELSE 1000000 * CAST(
                REGEXP_REPLACE(jp.salary_range, '[^0-9]', '') AS DECIMAL(10, 2)
            )
        END
        ELSE 1000000 -- Default score if no valid numeric data for salary
    END AS approximate_salary_in_vnd
FROM job_post jp
WHERE
    jp.application_deadline > CURRENT_DATE
    AND deleted_at IS NULL;

DROP VIEW IF EXISTS TopJobsView;

CREATE VIEW TopJobsView AS
SELECT
    jp.*,
    COUNT(appl.id) AS nums_of_applications,
    COUNT(appl.id) + (
        jp.approximate_salary_in_vnd / 10000000
    ) AS favorability
FROM
    JobsWithApproximateSalary jp
    LEFT JOIN `application` appl ON jp.id = appl.job_id
GROUP BY
    jp.id
ORDER BY
    favorability DESC,
    nums_of_applications DESC,
    jp.approximate_salary_in_vnd DESC
LIMIT 100;

DROP VIEW IF EXISTS TopCompaniesView;

CREATE VIEW TopCompaniesView AS
SELECT
    company.*,
    CAST(
        ROUND(
            COUNT(jp.id) * AVG(
                jp.approximate_salary_in_vnd / 1000000
            )
        ) AS UNSIGNED
    ) AS favorability,
    COUNT(appl.job_id) AS total_applications
FROM
    company
    LEFT JOIN JobsWithApproximateSalary jp ON company.id = jp.company_id
    AND jp.application_deadline > CURRENT_DATE
    LEFT JOIN `application` appl ON jp.id = appl.job_id
WHERE
    company.deleted_at IS NULL
GROUP BY
    company.id
ORDER BY
    total_applications DESC,
    favorability DESC;

CREATE VIEW TopCategoriesView AS
SELECT
    jc.*,
    COUNT(appl.id) AS application_count,
    COALESCE(
        CAST(
            AVG(jp.approximate_salary_in_vnd) / 1000000 AS UNSIGNED
        ),
        0
    ) AS salary_score
FROM
    job_category jc
    LEFT JOIN JobsWithApproximateSalary jp ON jp.category_id = jc.id
    AND jp.application_deadline > CURRENT_DATE
    LEFT JOIN `application` appl ON appl.job_id = jp.id
GROUP BY
    jc.id
ORDER BY
    application_count DESC,
    salary_score DESC
LIMIT 100;