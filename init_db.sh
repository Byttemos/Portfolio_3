#!/bin/sh
DB_NAME="data.db"
sqlite3 $DB_NAME ".import students_data.csv Students"
sqlite3 $DB_NAME ".import city_data.csv Cities"
sqlite3 $DB_NAME ".import registrations_data.csv Registrations"
sqlite3 $DB_NAME ".import Courses_data.csv Courses"
