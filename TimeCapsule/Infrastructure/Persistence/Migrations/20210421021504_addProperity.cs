using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace TimeCapsule.Infrastructure.Persistence.Migrations
{
    public partial class addProperity : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Assess",
                table: "Periods");

            migrationBuilder.AlterColumn<double>(
                name: "AchievementPerHour",
                table: "Tasks",
                nullable: false,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AddColumn<DateTime>(
                name: "BeginTime",
                table: "Tasks",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<bool>(
                name: "IsOften",
                table: "Tasks",
                nullable: false,
                defaultValue: false);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "BeginTime",
                table: "Tasks");

            migrationBuilder.DropColumn(
                name: "IsOften",
                table: "Tasks");

            migrationBuilder.AlterColumn<int>(
                name: "AchievementPerHour",
                table: "Tasks",
                type: "int",
                nullable: false,
                oldClrType: typeof(double));

            migrationBuilder.AddColumn<int>(
                name: "Assess",
                table: "Periods",
                type: "int",
                nullable: false,
                defaultValue: 0);
        }
    }
}
