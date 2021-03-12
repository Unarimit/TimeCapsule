using TimeCapsule.Domain.Common;
using TimeCapsule.Domain.Enums;
using System;

namespace TimeCapsule.Domain.Entities
{
    public class RegisterRequest : AudiableEntity
    {
        public Guid Id { get; set; }

        public string Username { get; set; }

        public string Password { get; set; }

        public string Email { get; set; }

        public string Desc { get; set; }

        public RegisterRequestStatus Status { get; set; }
    }
}
