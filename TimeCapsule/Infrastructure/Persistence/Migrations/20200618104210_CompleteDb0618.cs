using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace TimeCapsule.Infrastructure.Persistence.Migrations
{
    public partial class CompleteDb0618 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Periods_Dailies_TimeDailyId",
                table: "Periods");

            migrationBuilder.DropForeignKey(
                name: "FK_Tasks_TaskClasses_ClassId",
                table: "Tasks");

            migrationBuilder.DropIndex(
                name: "IX_Tasks_ClassId",
                table: "Tasks");

            migrationBuilder.DropIndex(
                name: "IX_Periods_TimeDailyId",
                table: "Periods");

            migrationBuilder.DropColumn(
                name: "ClassId",
                table: "Tasks");

            migrationBuilder.DropColumn(
                name: "DeadLine",
                table: "TaskClasses");

            migrationBuilder.DropColumn(
                name: "IsCounting",
                table: "TaskClasses");

            migrationBuilder.DropColumn(
                name: "TimeDailyId",
                table: "Periods");

            migrationBuilder.AddColumn<DateTime>(
                name: "DeadLine",
                table: "Tasks",
                nullable: true);

            migrationBuilder.AddColumn<bool>(
                name: "IsCounting",
                table: "Tasks",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "IsFinish",
                table: "Tasks",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<Guid>(
                name: "TaskClassId",
                table: "Tasks",
                nullable: true);

            migrationBuilder.AddColumn<TimeSpan>(
                name: "TotalTime",
                table: "Tasks",
                nullable: false,
                defaultValue: new TimeSpan(0, 0, 0, 0, 0));

            migrationBuilder.AddColumn<Guid>(
                name: "DailyId",
                table: "Periods",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_Tasks_TaskClassId",
                table: "Tasks",
                column: "TaskClassId");

            migrationBuilder.CreateIndex(
                name: "IX_Periods_DailyId",
                table: "Periods",
                column: "DailyId");

            migrationBuilder.AddForeignKey(
                name: "FK_Periods_Dailies_DailyId",
                table: "Periods",
                column: "DailyId",
                principalTable: "Dailies",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_Tasks_TaskClasses_TaskClassId",
                table: "Tasks",
                column: "TaskClassId",
                principalTable: "TaskClasses",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Periods_Dailies_DailyId",
                table: "Periods");

            migrationBuilder.DropForeignKey(
                name: "FK_Tasks_TaskClasses_TaskClassId",
                table: "Tasks");

            migrationBuilder.DropIndex(
                name: "IX_Tasks_TaskClassId",
                table: "Tasks");

            migrationBuilder.DropIndex(
                name: "IX_Periods_DailyId",
                table: "Periods");

            migrationBuilder.DropColumn(
                name: "DeadLine",
                table: "Tasks");

            migrationBuilder.DropColumn(
                name: "IsCounting",
                table: "Tasks");

            migrationBuilder.DropColumn(
                name: "IsFinish",
                table: "Tasks");

            migrationBuilder.DropColumn(
                name: "TaskClassId",
                table: "Tasks");

            migrationBuilder.DropColumn(
                name: "TotalTime",
                table: "Tasks");

            migrationBuilder.DropColumn(
                name: "DailyId",
                table: "Periods");

            migrationBuilder.AddColumn<Guid>(
                name: "ClassId",
                table: "Tasks",
                type: "char(36)",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "DeadLine",
                table: "TaskClasses",
                type: "datetime(6)",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<bool>(
                name: "IsCounting",
                table: "TaskClasses",
                type: "tinyint(1)",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<Guid>(
                name: "TimeDailyId",
                table: "Periods",
                type: "char(36)",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_Tasks_ClassId",
                table: "Tasks",
                column: "ClassId");

            migrationBuilder.CreateIndex(
                name: "IX_Periods_TimeDailyId",
                table: "Periods",
                column: "TimeDailyId");

            migrationBuilder.AddForeignKey(
                name: "FK_Periods_Dailies_TimeDailyId",
                table: "Periods",
                column: "TimeDailyId",
                principalTable: "Dailies",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_Tasks_TaskClasses_ClassId",
                table: "Tasks",
                column: "ClassId",
                principalTable: "TaskClasses",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
