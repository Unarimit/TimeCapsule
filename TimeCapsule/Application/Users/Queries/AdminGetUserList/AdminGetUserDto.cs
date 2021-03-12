using System;
using System.Collections.Generic;
using System.Text;
using TimeCapsule.Application.Common.Mapping;
using TimeCapsule.Domain.Entities;

namespace TimeCapsule.Application.Users.Queries.AdminGetUserList
{
    public class AdminGetUserDto : IMapFrom<User>
    {
        public string Username { get; set; }

        public string Password { get; set; }

        public string Email { get; set; }

        public bool IsAdmin { get; set; }

        public bool IsUser { get; set; }

        public string CreatedBy { get; set; }

        public DateTime Created { get; set; }
    }
}
